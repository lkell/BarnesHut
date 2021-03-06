# BarnesHut
Simulate and animate n-body gravitational interactions using the Barnes-Hut algorithm. The BarnesHut class can be used to simulate the motion of gravitational bodies. The Animator and Exporter classes can be used to respectively visualize or export the trajectories of a simulation.

Below is a clip of of a Barnes Hut Animator showing the interaction of of two "disk" galaxies.

![Video demo](https://user-images.githubusercontent.com/24996165/50474096-d1163900-097c-11e9-8167-066d7aeaf71f.gif)

The Barnes Hut Simulation calculates the resulting gravitational force on each body by creating a quadtree (or an oct tree in three dimensions) of all of the bodies for each step of the simulation. The QuadrantAnimator can be used to animate the quad tree evolution in a simulation. Below is a frame output of a QuadtrantAnimator animation.

![Quad frame](https://user-images.githubusercontent.com/24996165/50536586-38fe8800-0b13-11e9-8ac1-00ddbe1b2abd.PNG)

## Future Updates
Future updates to this project will include:
- A Swing-based graphical user interface that will be used to create, run, save, and import animations. This interface will make use of the classes defined in this project.
- A vectorless implementation of the Barnes Hut simulation. Using the custom Vector2D class aids in redability, but using primitive types in place of constantly creating new vector objects will improve the simulator performance.
- Additional integration methods.
- A ConfigurationValidator class to validate the structure and content of a Simulation configuration file before that file is used to create a simulation. This class will output any error descriptions to the client.
- Updates to the simulator input to allow physical units to be specified for the simulation. The Animators will make the appropriate conversion calculation to allow the simulation to be displayed on the screen.
- Updates to the Animator classes so that a maximum framerate can be specified.
- Updates to the LargeExporter class so that:
  - A time index column is added to the Trajectories CSV.
  - A maximum output file size can be specified.
  - A desired "framerate" can be specified so that exported files can be reduced in size. The Exporter will omit steps in the simulation accordingly.
- Additional GalaxyBuilder methods including methods for a more accurate disk galaxy, including a more accurate model for the central massive bulge as well as the decay rates. Updates will also include methods for a Ring galaxy, and optional models using Dark Matter and MOND. 

## About the Simulation
Here are some useful links on the Barnes-Hut algorithm: 
https://en.wikipedia.org/wiki/Barnes%E2%80%93Hut_simulation 
http://arborjs.org/docs/barnes-hut.

For learning how to implement the algorithm, [this page](http://beltoforion.de/article.php?a=barnes-hut-galaxy-simulator) contains useful explanations, and [this page](http://www.cs.princeton.edu/courses/archive/fall03/cs126/assignments/barnes-hut.html) contains a helpful API, some of which influenced this implementation.
