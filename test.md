## What's pandas?  
* Open-source library that offers data structure support and a great set of tools for data analysis, great for the analysis of tabular data (data that's arranged in rows and columns).
* Makes Python formidable competitor to R and other data science tools.
* Pandas is used in everything from simple data manipulation to complex machine learning
## Why optimize Pandas
That actually kind of depends on your use case. If you're doing data science where your runtimes don't matter and your data isn't huge, maybe it doesn't actually matter all that much.
* Pandas built on top of Numpy and Cython, making it very fast when used correctly which means that it can be very fast when it's used
correctly and those optimizations that you can put into it can make the difference between your code running in minutes or your code running in milliseconds.
## Benchmarking - how slow is my code?
how do I know whether my code is slow and how do I know whether I'm making it faster.
We're going to talk a little bit about how you would do benchmarking so to start we're going to be looking at a bunch of
examples that are based on a data set that I pulled down also of Expedia developer website and it contains all of the hotels in New York states that are sold by Expedia.  
There's about 1600 of them turns out there's a lot of hotels in New York
State and our data set is going to contain some information with one hotel per row an ID for each hotel a name and
address a latitude and longitude and some information about the hotel such as the star rating and some high rates and low rates that are calculated by Expedia and the third we're just going to grab a function that we can benchmark from this
is an example of a normalization
function it's pretty straightforward
what it's actually doing is not actually
all of that relevant for our purposes
other than it's taking some means and
some standard deviations cutting off
outliers and taking a log to normalize
what we care about is how do we actually
figure out how to time this
and which parts of it are slower or
faster than others so we're going to do
that was something called magic commands
so magic commands are available through
Jupiter notebooks to provide additional
functionality on top of Python code that
may running that code or doing other
things with that code extra awesome and
useful magic commands you'll see start
with a percentage sign the single
percent sign is just code that's
executed on one line or double
percentage that's executed on an entire
cell and the first function that we're
going to talk about is the timeit
command so a timeit command just reruns
a function over and over and over again
and it shows the average and the
standard deviation of the runtime that
it obtained as a result what's the time
that it calculates can serve as a
benchmark for a bunch of further
optimizations so let's look at an
example so here we've done a timeit of
our normalized function I've said my
normalized function the data frame that
I created my set of hotels and I filled
it to use the series higher rate in
order to actually apply this function
and I've assigned the results to a new
series within the data frame have run
the payment and it's letting me know
that it the function on average took 2.8
84 milliseconds and it ran it seven to
seven runs of 100 loops each so now that ##########################
provides a baseline from which I can go
to figure out how to make this function
run faster or slower and figure out
whether I'm actually succeeding at what
I'm doing so the next thing I'm going to
do is I'm going to feed it through an
extension called line profiler which
gets abbreviated as % LP run as a magic
function an align profiler will run
through run my function line by line and
give me a bunch of useful statistics
about what it's doing and in particular
it's going to tell me in that second
column my hits it's going to tell me how
many times my
has been actually each line in my
function has been rerun so if I'm
running the function on a bunch of loop
Celsius number greater than one here and
in the next of the last column it's
going to tell me what percentage of the
time each line actually took so over
here for example if you look we see I
don't have my pointer here but if you
look about four lines up from the bottom
you'll see that my line 11 and 12
actually took well close to 80% of the
time that this function was running for
which means that if I were going in and
trying to optimize this function I would
certainly look at those two lines and
try to start there because I would get
the biggest bang for my bum for my buck
trying to optimize those steps so now
that we sort of get the sense of how
we're going to go about trying to
optimize or trying to benchmark our
functionality let's talk about some of
the slower methodologies that I've seen
used in pandas so we're going to start
with a different practice function this
is the hammer sign or Great Circle
distance function all this is basically
doing is taking in two sets of
coordinates and is calculating a
straight line distance between them
taking in the curvature of the earth
again the details of the implementation
aren't actually all that important for
our purposes other than to know that
it's doing some addition some
subtraction and it's doing a bunch of
sines and cosines and other trig to
actually calculate the distances and
it's returning the actual number of
miles between two coordinates so one
thing that I see people do a lot
actually as they start out in pandas is
start iterating through all of the rows
to apply a function which kind of makes
sense right because you're working with
a data frame that has a bunch of rows
and a bunch of columns and why can't
they just loop through all of my rows
just the same way that I would loop
through a list and actually figure out
and actually apply the function to each
item on that list this thing is
is actually built on numpy which is
designed for vector manipulation which
means that loops are inherently
inefficient
that being said pandas will give you
methods to loop through row by row if
that's something you really want to do
so for example it will provide you with
an it arose method or inner tuples
method which will give you essentially a
set of things to loop through but it
will be quite slow so over here I've
basically created a blank new dictionary
to feed our mileage into and I'm running
through my rows within data frame it
arose command and I'm just feeding it a
set of coordinates and I'm running it
calculating the distance between a
particular set of coordinates and every
single hotel and my data set if you're
curious a particular set of coordinates
happens to be the Brooklyn superhero
supply in New York it's a fantastic
place you should go there so now at this
point we know that our function with it
arose have taken 184 milliseconds we
don't actually know whether that's slow
or fast at this point right but all we
know is that it took less than a second
I guess that's good but we can try a
different looping method a nicer way to
have done this would have been to use
apply which applies a function along a
specified axis which is to say a set of
rows or a set of columns and it's a lot
more efficient than it arose it's much
more optimized even though it's still
looping through over and over again a
platter is best used only when there is
no good way to actually vectorize your
function so let's try looking at that so
over here I'm essentially having the
function do the same saying it's
applying the function to each individual
row and it's comparing the distance from
that hotel to a particular set of
coordinates and it's running it through
a row by row but just by swapping out
apply for it arose I've gotten my run
time down to 78 milliseconds by
comparison
to the 184 milliseconds that we saw
before that's a two and a half times
improvement for basically changing
nothing about my function just changing
the function that runs it and now if we
look at the actual line profiler we can
see what it's doing well uprize doing a
lot of repetitive repetitive steps where
you see that 100 1631 hits that's the
apply function hitting the each
individual row and doing the same set of
things over and over and over again and
if we could just get rid of that
repetition we could make it run a lot
faster and that's what vectorization
actually does for our functions so what
is vectorization well before we start
talking about that let's take a step
back and just talk about what it is that
makes pandas so great the basic unit of
pandas is an array so there's two sort
of basic objects in pandas one is a
series which is a one-dimensional array
with axis labels so a series would be a
column with a column with some labels on
it for example or a data frame which is
a two-dimensional array with labeled
axes in other words that would be a
table with column labels and row labels
now vectorization is the process of
performing operations on arrays instead
of scalars in other words my vectorized
operations are going to take my entire
series and perform an operation on the
entire saying simultaneously instead of
running through it one single item at a
time why would I want to do that well
many pandas functions are actually built
to operate that way they're built
operate directly on a race so the
built-in pandas some functions the
string string processing they're all
vectorize functions and they are
inherently because of the inherent panda
structure much faster than regular
looping operations or trying to operate
on one piece of the data frame at a time
so let's look at how we would vectorize
this function
we actually don't need to do much all
we've done is we've said we're going to
grab our have a sine function we're
still using the same set of coordinates
but now instead of looping and applying
the function on one row at a time
we're going to feed it the entire vector
latitude and longitude that we've
grabbed from the data frame so at this
point my DF latitude in DF longitude are
individual arrays they're the contents
of both of those columns whole and this
brings our time down to actually 1.8
milliseconds so if you recall in our
previous in our previous swooping runs
we got it down to something like 73 or
something like that
now we are down to a tiny fraction of
that and in fact if we look at time it
well the function is no longer looping
it's doing exactly one hit to every
single line that were in the function
and that's what allows that huge
increase in efficiency and so now just
to summarize with it arose we were
starting out at 184 milliseconds with a
vectorized implementation of the exact
same function we're down to 1.8
milliseconds which is a 43 times
improvement over even looping was apply
so that's already pretty great but could
we make it even better well the answer
is actually yeah we can and we can do
that by vectorizing with numpy arrays
instead of series
so why numpy you might ask well numpy
calls itself a fundamental package for
scientific computing in Python numpy
operations are essentially executed an
optimized pre compiled code and
fundamental objects and numpy are also
arrays are called NZ arrays and they are
highly efficient and they skip out on a
lot of the overhead that gets incurred
by operations on pandas series and theis
on pandas series are great for a lot of
things they provide their own indexes
there they have a lot of functionality
but they do have a lot of extra overhead
that numpy array skip
and so what we can do is we can take
again our old good old Harrison function
and we're going to convert our pandas
series back to numb 5mk erase by
applying the dot values function to them
this is just the built-in pandas method
and now we are down to actually 370
microseconds which is to say we've
effectively gotten up to a 500 fold
improvement from our original version of
the function by not changing the
function essentially just changing the
way that our inputs are read in so that
probably is pretty great for what we're
trying to do and for that particular
function but you might say what if I
actually really wanted to use a loop and
there might be a couple of reasons for
why you might want to do that and then
this hammer sign function might not
actually be the best example for that
but there are other reasons so for
example maybe your function is really
complex and it doesn't yield itself
easily to vectorization maybe you're
calling an API and there is no way to
vectorize this process may be trying to
vectorize your function would actually
incur a lot of memory overhead for
example your data frame is huge and it
contains a lot of really complex float
operations and it's too much for you to
handle and so you're actually it's
actually preferable for you to loop even
though it would be slower or maybe
you're just plain stubborn I don't know
so one of the things that we could use
to actually speed up loops is size on so
say sign language is a superset of
Python that additionally supports
calling C functions and declaring C
types and I think there is another slice
on talk following directly after this
I'm sure they will know a lot more than
I do about it but this is my abbreviated
version so any almost any piece of
Python code is also valid seisonn code
and the syphon compiler will effectively
convert Python code into C code which
will make equivalent calls to the Python
and C API
so we're still using jupiter notebooks
and i've slip installed this ison
extension I'm going to load my sights on
extension and then I'm going to open a
new site on sale I'm going to initialize
it as running incise on and I'm going to
grab my entire have a sine function and
pretty much just run it through that
slice on compiler I've not really made
any real changes to it other than to
define it in my death as a CP death
which is a slice on Python function
instead of just a straight Python
function so let's time this once that's
been done I can basically just run my
function exactly the way I did before
with an apply and I'm down to 76
milliseconds well unfortunately that's
actually not very good it's about the
same thing we were getting with Python
and that's a little bit disappointing
because the sights on compiler was
supposed to be doing a bunch of work in
the background to convert our code to 2c
or slice on and try to optimize it and
make it run faster so what happened
there well if I add the - a option to my
sites on magic command the second
compiler will actually show me how much
of this function has been able to
convert from Python to size on and those
yellow bits is everything that it's
still running in python that's a lot of
yellow we did not do very well and
that's exactly why we didn't cut any
time off so let's see what we can
actually do about that well for one
thing we know that as long as slice on
is still using Python we're not going to
we're not going to improve the time much
so we need to make the function more
slice unfriendly and there's a bunch of
ways to do that you know anything
ranging from taking the real function to
converting it to pure C but two of the
things that we can do fairly easily is
we can add explicit strict typing to the
function or we can replace our wealth or
and/or
or we can replace our Python and numpy
libraries with C specific math libraries
so if you recall our function was using
a whole bunch of numpy math to calculate
a bunch of those trig formulas we're
going to take those out and we're going
to replace them with a actual C math
library since this is what our converted
function looks like now and you can see
that I've basically taken all of the
variables that are declared within the
function and I've added strict typing to
them they're all slopes it's easy enough
and I've imported the Lib C math to bring in a bunch of sine and cosine functions that we're going to be using to replace the numpy library other than
that the only thing I've really done is
there was one function which was a
degrees to radians conversion that
exists in numpy but not in the original
C library I've just taken it out and
rewritten it and defined it as a
function separate function within here
so now let's try running that so we're
going to time the sizin eyes function
again the actual apply statement looks
exactly the same as before and now we're
actually down to 50 milliseconds which
if you recall is not nearly as great as
300 microseconds but you know it's an
improvement we've gotten it up to 1.6
times the previous version which was
just running the Python function with
apply or running the row wise function
python function directly in the syphon
compiler without doing anything and so
at this point if you're really attached
to loops you can go into a lot more
probably seisonn optimizations but they
will get a lot more complex or you can
see if you can figure out a way to
vectorize your function which will give
you a huge boost and improvement in
performance for very little work and
one step if we actually look at our code
at this point well we see that there's a
hell of a lot more yield less yellow and
more white on the screen so our changes
that we made including the data typing
and the conversion to a different
library have actually done a lot to help
the function convert to actual sites on
code and run more efficiently alright so
here we are let's sum it up so again
this is our scoreboard we started out
with 184 milliseconds we got it down to
a point for my croissant 4 milliseconds
overall about a 500 time improvement we
started out looping with it arose which
you should pretty much almost never do
we moved to looping with a ply which is
a fairly efficient way to loop through
things we tried looping with a function
that got converted to slice on which
gave us some performance boost but not a
whole lot
and then we vectorize their function
with pandas and with numpy arrays which
gave huge boosts and performance so to
summarize the general Zen of pandas
optimization is one avoid loops if you
can if you must move use apply not
iteration functions if you must apply
you slice on to make it faster
vectorization is usually better than
scalar operations and vector operations
on numpy arrays are more efficient than
a native pandas series and with that
just a couple of words of warning to
quote xkcd premature optimization is the
root of all evil so before you start
optimizing your function make sure that
it is doing what you wanted to be doing
otherwise you will find yourself having
spent a week on trying to make it run a
few minutes faster just to find that it
actually wasn't running and doing things
that you wanted it to do at all you will
regret it another caveat that I should
put in here all of this was done in
Python 3.5 one
panda's point 20 so anything any of
these results will obviously vary
depending on exactly how you're using
your functions the system that you're
running it on the size of the data frame
the type of the functions that you're
using and so take it with a grain of
salt I can promise you the exact same
performance performance boosts that we
saw on this particular data frame in
this particular function for every
single thing that you might ever apply
these methods to and that's pretty much
it and since I have a few more seconds
I'm going to give a bonus pitch we are
hiring and check us out at upside comm
we're a fantastic small travel startup
located in Washington DC and if you're
interested come talk to me thank you
very much Thank You Sophia does anyone
have any questions if you do there are
microphones in the aisles
so in your in your habits on Saipan
example your last yellow line was uh
you're using map and tuple unpacking and
it seems like if you just put four lines
of just do the computation you could
have avoided a complete round trip into
Python land you know that's a fair point
I actually did try doing that and it
actually does not improve performance
but it was actually one one of the
things that I was going to investigate
was figuring out how to actually make
that run faster because you're right it
does run as a Python function but for
the purposes of that function doing it
one way or another didn't make a
difference so I left it as it was thank
you Oh for your improvement for your
psyche on have a sine function did you
test that with the vectorization to see
if there was further improvement so not
quite because once well so if you were
to run it on vectors a lot of the actual
benefits of sizin would basically just
be the benefits that you get from the
direct vectorization and so yes you
could but at that point you might as
well just run that function on vectors
in its Python form and it will still be
way fast there the combining the two
doesn't actually give you much of a
boost in performance in that case thank
you so this isn't exactly related to I
thought our pandas but you're using
lambdas and I know I remember way back
west McKinney's advice was not to use
lambdas and apply has that changed or so
that is a great question I don't know if
I've ever heard not to use lambdas in
apply the reason that we use lambdas in
this particular use case is because
we're trying to apply to a row and so
we're trying to we need to give that
object a particular name right if I were
not applying it to a row if I were just
doing it on a single series I could do
without it
you know if that's faster
that's a great question I don't think it
would be but I haven't tested it so I
don't know all right thank you that's
great Hoss question group by to group
our regression models today and I found
that if you do like over more than four
columns
it just is increasingly not performance
at all do you have any suggestions of
other ways to do that that would be more
performant for specifically group bys
it's you know off the top of my head I
would guess that maybe dealing with some
of the data types and looking at the way
that your data types of the things that
you're grouping by might affect it but
be honest answer is no I don't actually
know and so we had time for please again
