# BarnesHut
Simulate and animate n-body gravitational interactions using the Barnes-Hut algorithm. The BarnesHut class can be used to simulate the motion of gravitational bodies. The Animator and Exporter class can be used with a Simulator object to respectively visualze or export the trajectories of the simulation.

[!Video deomo](https://user-images.githubusercontent.com/24996165/50474096-d1163900-097c-11e9-8167-066d7aeaf71f.gif)

## Future Updates
Future updates to this project will include:
- A Swing-based graphical user interface that will be used to create, run, save, and import animations. This interface will make use of the classes defined in this project.
- A vectorless implementation of the Barnes Hut simulation. Using the custom Vector2D class aids in redability, but using primitive types in place of constantly creating new vector objects will improve the simulator performance.
- Additional integration methods.

## About the Simulation
Here are some useful links on the Barnes-Hut algorithm: 
https://en.wikipedia.org/wiki/Barnes%E2%80%93Hut_simulation 
http://arborjs.org/docs/barnes-hut.

For learning how to implement the algorithm, [this page](http://beltoforion.de/article.php?a=barnes-hut-galaxy-simulator) contains useful explanations, and [this page](http://www.cs.princeton.edu/courses/archive/fall03/cs126/assignments/barnes-hut.html) contains a helpful API, some of which influenced this implementation.
