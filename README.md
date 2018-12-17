# Interactive Chaos Game
An interactive version of the famous Chaos Theory based "Chaos Game", with keyboard controls to change the number of vertices, point plotting speed, distance travelled by tracepoint, etc.

This was developed based on the code available at Rosetta Code:
https://www.rosettacode.org/wiki/Chaos_game#Java

First of all, for any non-brazilians looking at this repository (The odds of this happening are low, but I can't rule it away :D), forgive me for mixing up English and Portuguese inside the code. Unfortunately, I haven't had the time to translate the code in its entirety to english, but I'm hoping to do so in the near future.

The Chaos Game consists of N fixed points (vertices), and a tracepoint. The tracepoint starts at a random point, preferrably in the vicinity of the vertices. The tracepoint then chooses one of the N vertices at random (The probability of choosing any of the N vertices is equal) and proceeds to travel a certain distance between itself and the chosen vertex (It's a standard to make the tracepoint travel halfway through, but this isn't a rule). After repeating this iterative process a large number of times, this will often produce a fractal shape.
