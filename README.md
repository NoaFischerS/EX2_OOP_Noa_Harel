**First part:**

**DWGraph\_DS:**

this class represents a directional weighted graph.

**Parameters:**

|
 |
|
| --- | --- |
|
|
|

HashMap\&lt;integer, node\_data\&gt;graph – keys of nodes and the node

HashMap\&lt;integer, edgeList\&gt;ni – key of nodes and his neibors

hashMap\&lt;integer, collection\&lt;Integer\&gt;\&gt;destSet – from where to where the edge goes

int edgeSize - weight

int MC – actions on graph

**Functions:**

**public node\_data getNode(int key) -** returns the node\_data by it&#39;s unique key.

param - key.

**public edge\_data getEdge(int src, int dest) -** returns the data of the edge (src,dest), null if none.

run in O(1) time.

param - src, dest.

**public void addNode(node\_data n) -** adds new node to the graph with the given node\_data.

run in O(1) time.

**public void connect(int src, int dest, double w) -** Connects an edge with weight w between node src to node dest.

run in O(1) time.

- **public Collection\&lt;node\_data\&gt; getV( ) -** This method returns a pointer (shallow copy) for the collection representing all the nodes in the graph

run in O(1) time.

return Collection\&lt;node\_data\&gt;.

**public Collection\&lt;edge\_data\&gt; getE(int node\_id) -** This method returns a pointer (shallow copy) for the collection representing all the edges getting out of the given node (all the edges starting (source) at the given node).

run in O(k) time.

return Collection\&lt;edge\_data\&gt;.

**public node\_data removeNode(int key) -** Deletes the node (with the given ID) from the graph This method should run in O(k), V.degree=k, as all the edges should be removed and removes all edges which starts or ends at this node.

return the data of the removed node (null if none).

param - key.

**public edge\_data removeEdge(int src, int dest) -** deletes the edge (src, dest) from the graph.

run in O(1) time.

return the data of the removed edge (null if none).

param - src, dest.

**public int nodeSize() -** returns the number of vertices (nodes) in the graph.

run in O(1) time.

**public int edgeSize() -** returns the number of edges (assume directional graph).

nt - for testing changes in the graph.this method should run in O(1) time

public int getMC() - retuens number of changes on graph.

**DW\_Graph\_Algo**
This class represents a Directed (positive) Weighted Graph Theory Algorithms including:
0. clone(); (copy)
1. init(graph);
2. isConnected(); // strongly (all ordered pais connected)
3. double shortestPathDist(int src, int dest);

4. List\&lt;node\_data\&gt; shortestPath(int src, int dest)
5. Save(file); // JSON file
6. Load(file); // JSON file

**Parameters:**

privatedirected\_weighted\_graph graph – the graph

**public void init(directed\_weighted\_graph g) -** init the graph on which this set of algorithms operates on.

param - g.

**public directed\_weighted\_graph getGraph() -** return the underlying graph of which this class works.

**public directed\_weighted\_graph copy() -** compute a deep copy of this weighted graph.

**public boolean isConnected() -** returns true if and only if there is a valid path from each node to each other node.

**public double shortestPathDist(int src, int dest) -** returns the length of the shortest path between src to dest (if no such path returns -1).

param - src start node, dest target node.

**public List\&lt;node\_data\&gt; shortestPath(int src, int dest) -** returns the the shortest path between src to dest - as an ordered List of nodes:
src--\&gt; n1--\&gt;n2--\&gt;...dest

param - src start node, dest target node.

**public boolean save(String file) -** saves this weighted (directed) graph to the given
file name - in JSON format

param - file the file name.

return - true iff the file was successfully saved.

**public boolean load(String file) -** this method load a graph to this graph algorithm if the file was successfully loaded - the underlying graph of this class will be changed (to the loaded one), in case the graph was not loaded the original graph should remain &quot;as is&quot;.

param - file name of JSON file.

return - true iff the graph was successfully loaded.

**EdgeData**

This class represents the set of operations applicable on a
directional edge(src,dest) in a (directional) weighted graph.

**Parameters:**

| Int s - sorce |
| --- |
|
|
|
|
|
|
|
|
| Int d – destination of edgeDouble W - weightString info – information savedInt tag – information saved |

**public int getSrc() -** get the ID of the source node of this edge.

**public int getDest() -** get the ID of the destination node of this edge.

**public double getWeight() -** return the wwight of this edge (positive value).

**public String getInfo() -** returns the remark (meta data) associated with this edge

**public void setInfo(String s) -** allows changing the remark (meta data) associated with this edge.

param - s string

**public int getTag() -** get temporal data which will be used for algorithms.

**public void setTag(int t) -** allows setting the &quot;tag&quot; value for temporal marking an edge, for marking by algorithms.

param - t the new value of the tag.

**GeoLocation**

This class represents a geo location \&lt;x,y,z\&gt;, aka Point3D.

**Parameters:**

Double x,y,z – location of a 3D object

**Functions:**

**public String toString() -** return string of point.

**public double distance(geo\_location g) -** return distance between 2 points.

**NodeData**

This class represents the set of operations applicable on a
node (vertex) in a (directional) weighted graph. Each node has a unique key.

**Parameters:**

Int key – unique key of node

Int tag – to save visitation in node

String info – was visited

Geo Location – geographic 3D location of node

**Functions:**

**public int getKey() -** returns key of the given node.

**public geo\_location getLocation() -** return location of this node, if none return null.

**public void setLocation(geo\_location p) -** allows changing location of this node.

**public String getInfo() -** return meta data associated with this node.

**public void setInfo(String s) -** allows chaging meta data of this node

param - s string

**public int getTag() -** get temperoral data which will be used in algorithms.

**public void setTag(int t) -** allowes setting the tag value for temporal marking a node, will be used for algorithms.
}

_ **Second part** _

**Arena**

This class represents a multi Agents Arena which move on a graph - grabs Pokemons.

**Parameters:**

private directed\_weighted\_graph graph - the graph which the game is running on

private List\&lt;CL\_Agent\&gt; agentsList - all agents of game

private List\&lt;CL\_Pokemon\&gt; pokemonList - all pokemons of game

private List\&lt;String\&gt; info

private static Point3D MIN

private static Point3D MAX

private game\_service game - game from server

**Functions:**

**public Arena() -** constractur

**public void setPokemons(List\&lt;CL\_Pokemon\&gt; p) -** allows updating pokemon list

**public void setAgents(List\&lt;CL\_Agent\&gt; a) -** allows updating pokemon list

**public long getTime\_left() -** time left to end game

**public game\_service getGame() -** get game from server

**public void setGame(game\_service game) -** allows updating game

**public void setGraph(directed\_weighted\_graph g) -** allows updating graph

**private void init() -** init arena

**public List\&lt;CL\_Agent\&gt; getAgents() -** get list of agents of the game

**public List\&lt;CL\_Pokemon\&gt; getPokemons() -** get list of pokemons of the game

**public directed\_weighted\_graph getGraph() -** get the graph of the game

**public**** List\&lt; ****String**** \&gt; ****get\_info**** () **** –** return info

**public static List\&lt;CL\_Agent\&gt; json2Agent(String s, directed\_weighted\_graph g) -** creates and return agent list from json/string

**public static ArrayList\&lt;CL\_Pokemon\&gt; json2Pokemons(String fs) -** creates and return Pokemon list from json/string

**public static void updateEdge(CL\_Pokemon p, directed\_weighted\_graph g) -** updates the edge where the pokemon is located

**private static boolean isOnEdge(geo\_location p, geo\_location src, geo\_location dest ) -** checks if the object is on the edge by location and returns true or false

**private static boolean isOnEdge(geo\_location p, int s, int d, directed\_weighted\_graph g) -** checks if the object is on the edge by an edge and returns true or false

**private static boolean isOnEdge(geo\_location p, edge\_data e, int type, directed\_weighted\_graph g) -**

**private static Range2D GraphRange(directed\_weighted\_graph g) -** the 2D size of the graph

**public static Range2Range w2f(directed\_weighted\_graph g, Range2D frame) -** allows to change the size of the graph when screen gets smaller or bigger

**CL\_Agent**

This class represents a agent. the agent has speed and he needs to eat as much as valubale pokemons as he can. By eating pokemons the agent adds to his score.

**public CL\_Agent(directed\_weighted\_graph g, int start\_node) -** constractur

**public void update(String json) -** updates the agents from JASON

**public int getSrcNode() -** get the node where the agent started

**public String toJSON() -** turns stirng to JASON

**private void setMoney(double v) -** allows updating the score of an agent

**public boolean setNextNode(int dest) -** decieds where the agent will go next

**public void setCurrNode(int src) -** allows updating source node

**public boolean isMoving() -** returns true iff the agent is already on his way to next node

**public int getID() -** get ID of agent

**public geo\_location getLocation() -** get location of agent

**public double getValue() -** return the score of the agent

**public int getNextNode() -** return the next node of this agent

**public double getSpeed() -** return the speed of this agent

**public void setSpeed(double v) -** allows setting the speed of this agent

| **public**** long ****getTimeAfterEat**** () **** -** check the time to sleep to reduce moves by distance and weight, after the pokemon is eaten
**public**** long ****timeSleep**** () **** -**check the time to sleep to reduce moves by distance and weight
**public**** edge\_data ****get\_curr\_edge**** () ****-** return the edge the agent is on
**public**** CL\_Pokemon ****getPokemonDest**** () **** –** return the next pokemon that should be eaten
**public**** void ****setPokemonDest**** ( ****CL\_Pokemon**** curr\_fruit****)**  **–** allows setting the next Pokemon
|
|
| --- | --- |
|
|
|

**private**** double ****getDisOnEdge**** () ****-** for sleeping time purposes

**CL\_Pokemon**

This class represents a pokemon, pokemons have values and they are located on the edges of the graph.

**public CL\_Pokemon(Point3D p, int t, double v, double s, edge\_data e) -** constructor

**public static CL\_Pokemon init\_from\_json(String json) -** get the Pokemons from JASON

**public edge\_data get\_edge() -** return the edge that the pokemon stands on

**public void set\_edge(edge\_data \_edge) -** allows setting the edge of this Pokemon

**public Point3D getLocation() -** return the 3D location of this Pokemon

**public int getType() -** return type of this Pokemon if going up or down

**public double getValue() -** return the value of this Pokemon

**public CL\_Agent getEatAgent() -** return the agent that eats this Pokemon

**public void setEatAgent(CL\_Agent agent) -** allows setting the agent that eats this pokemon

**public boolean equals(CL\_Pokemon p) -** returns true iff Pokemon p and this pokemon are equal

**Ex2\_Client**

**public Ex2\_Client(int level ,int id) -** constructor

**public static void startGame(int level, int id) -** method to start a game by the level and ID that he gets

**public void run() -** runs the game by taking the information from the given server using GSON

**private static long moveAgants(game\_service game, directed\_weighted\_graph graph) -** moves the agents along the edge if is on node searches next cloesest pokemon

**private static void updatePokToAgent(List\&lt;CL\_Agent\&gt; agentsList) –** tried to make better, not in use

**private static void updateAgentToPok(List\&lt;CL\_Pokemon\&gt; pokemonList) -** tried to make better, not in use

**private static long delayTime(game\_service game, directed\_weighted\_graph graph) -** tried to calculate the delay time, to reduce the moves, didn&#39;t succeced

**private static boolean eatPok(CL\_Agent a) -** returns true iff the agent eats this pokemon

**private static int nextNode(game\_service game,directed\_weighted\_graph g,CL\_Agent agentPlayer) -** return the next node for this agent&#39;s step, by finding the closet pokemon

**public static CL\_Pokemon closetPokemon(directed\_weighted\_graph g, CL\_Agent agent) -** tells agent which pokemon is the closest to him, if another agent eats it return null

**private static Queue\&lt;CL\_Pokemon\&gt; pokemonScore() -** PriorityQueue for the highest pokemon score to start with

**private void init(game\_service game) -** init a game from the server

_ **Graphics** _

**MainPanel**

**public void initImages() -** init the imges of the game

**public MainPanel() -** constractur

**public void paintComponent(Graphics g) -** repaint the game

**public void initArena(Arena ar, int frameWidth, int frameHeight) -** init the gui of arena

**private void updateW2f(int frameWidth, int frameHeight) -** makes the frame resizable

**private void drawTitle(Graphics g) -** for the images to be in good scale

**private void drawInfo(Graphics g) -** draws information on screen during game is running

**private void drawGraph(Graphics g) -** draws graph

**private void drawPokemons(Graphics g) -** puts pokemon images in the right place

**private void drawAgents(Graphics g) -** puts agent images in the right place

**private void drawScore(Graphics g) -** prints score of each agent and all of them togther, and does it resizable muching the screen

**private void drawNode(node\_data n, int r, Graphics g) -** draws node of the graph

**private void drawEdge(edge\_data e, Graphics g) -** draw edge of the graph

**LoginPanel**

**LoginPanel() -** constractur. adds window to enter ID and window to enter the level of the game. Adds a title to the opening panel

**public void actionPerformed(ActionEvent ae) -** reacts to the Id and level that are submitted by user by applaying the right level. If the entered level does not exist reopens the window

**MainFrame**

**public MainFrame(String title, Arena arena) -** constractur that builds a frame for the game

**public void updateGraphic() -** repaints the graphic by calling the responsable function in MainPanel

**private void initFrame() -** inits the frame in the right size, and closes it when user is closing game

**private void initPanel(Arena arena) -** init panel by calling MainPanel and adds it to frame