# traffic_simulator

This Java project, a traffic simulator implemented in two parts, stands out for its robust approach and the application of advanced programming concepts. 

**Authors:** Notkero Gómez Fernández and Iván Hernández Roldán.

Below, the key aspects and theoretical concepts implemented in both parts of the project are summarized:

## Part 1: Traffic Simulator 

### <u>Implemented Concepts:</u>

#### Object-Oriented Design:

- Code structure based on classes and objects.
- Use of inheritance and polymorphism to model simulated objects.

#### Generics and Collections in Java:

- Use of generics for flexibility and code reusability.
- Efficient data handling through collections like lists and maps.

#### Input/Output and JSON Manipulation in Java:

- Analysis and creation of JSON data to represent simulator input and output.
- Utilization of libraries for efficient JSON structure handling.

## Part 2: Graphical User Interface (GUI) - MVC Development

### <u>Implemented Concepts:</u>

#### Model-View-Controller (MVC) Pattern:

- Implementation of the MVC pattern to clearly separate business logic (model) from the user interface (view).
- Definition of interfaces TrafficSimObserver and Observable for observers and observables, respectively.

#### Event Handling and Observers:

- Integration of events and observers to notify UI changes in response to simulator changes.
- Dynamic updating of the graphical interface based on simulator changes.

#### Thread Execution Management:

- Use of threads to prevent UI blocking during simulation execution.
- Ensuring continuous UI responsiveness even during an ongoing simulation.

#### Exception and Error Handling:

- Implementation of exception handling to ensure simulator stability and robustness.
- Elegant notification of errors.

#### Graphic Interface Visually Appealing:

- Design of an intuitive and efficient graphical interface using Swing components.
- Implementation of an interactive control panel to manage the simulation.

#### Graphical Representation of Road Map:

- Development of graphical viewers that clearly represent the state of the road map and the simulation.
- Use of images and visual representations for weather conditions and pollution.
