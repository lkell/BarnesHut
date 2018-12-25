# BarnesHut
Simulate and animate n-body gravitational interactions using the Barnes-Hut algorithm. The BarnesHut class can be used to simulate the motion of gravitational bodies. The Animator and Exporter class can be used with a Simulator object to respectively visualze or export into a JSON output file the the trajectories of the simulation.


## Future Updates
Future updates to this project will include:
- An Exporter class that can be used to export the trajectories of all particles during a simulation into a file.
- Updates to the Animator class so that it can create an animation using a Barnes Hut trajectories export file.
- A vectorless implementation of the Barnes Hut simulation. Using the custom Vector2D class aids in redability, but constantly creating new objects instead of just using primitive types decreases the performance of the simulator.
- Additional integration methods.

## About the Simulation
Here are some useful links on the Barnes-Hut algorithm: 
https://en.wikipedia.org/wiki/Barnes%E2%80%93Hut_simulation 
http://arborjs.org/docs/barnes-hut.

For learning how to implement the algorithm, [this page](http://beltoforion.de/article.php?a=barnes-hut-galaxy-simulator) contains helful explanations and pseudocode, and [this page](http://www.cs.princeton.edu/courses/archive/fall03/cs126/assignments/barnes-hut.html) contains a helpful API, some of which influenced this implementation.
