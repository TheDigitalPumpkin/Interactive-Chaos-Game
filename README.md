# Interactive Chaos Game
An interactive version of the famous Chaos Theory based "Chaos Game", with keyboard controls to change the number of vertices, point plotting speed, distance travelled by tracepoint, etc.

This was developed based on the code available at Rosetta Code:
https://www.rosettacode.org/wiki/Chaos_game#Java

# How the Chaos Game works:
The Chaos Game consists of N fixed points (vertices), and a tracepoint. The tracepoint starts at a random point, preferrably in the vicinity of the vertices. The tracepoint then chooses one of the N vertices at random (The probability of choosing any of the N vertices is equal) and proceeds to travel a certain distance between itself and the chosen vertex (It's a standard to make the tracepoint travel halfway through, but this isn't a rule). After repeating this iterative process a large number of times, this will often produce a fractal shape.

# Some side notes:
First of all, for any non-brazilians looking at this repository (The odds of this happening are low, but I can't rule it away :D), forgive me for mixing up English and Portuguese (I'm Brazilian) inside the code. Unfortunately, I haven't had the time to translate the code in its entirety to english, but I'm hoping to do so in the near future.

Second of all, I apologize for any workarounds you may come across while looking at the code. This is due to the fact that this entire thing was an assignment for my Object Oriented Programming course at Uni, and unfortunately I was doing the assignment all by my own. I promise I will refactor this later. I know it has plenty of limitations (Such as not being able to define where the tracepoint or vertices will begin, and only having a maximum of 5 verteices), but I do intend to expand upon this in the future.
