# mTSP Heuristic Optimization Engine

Developed a backend optimization engine to solve the Multiple Traveling Salesman Problem (mTSP), generating and evaluating over 5,000,000 iterations.

## Architecture & Design Patterns
Implemented the **Strategy Design Pattern** to seamlessly orchestrate 5 distinct cross-route mutation operations (SwapNodes, InsertNode, etc.) ensuring high extensibility and clean architecture. Engineered a robust state-rollback mechanism using deep copy constructors to prevent object reference leaks during the heuristic evaluation phase.

## Performance
Achieved a ~70% reduction in total network routing cost (from ~51,000 km to ~14,000 km) utilizing Hill Climbing algorithms.

## Technologies Used
* Java 11+
* Maven
* JewelCLI
* Gson
