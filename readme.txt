Assignment 1 - Readme

[depth], [numCreated], [numExpanded], [maxFringe]

BFS Output: 
    Time Complexity: O(4^d)  d = depth
1.
java FifteenProblem "123456789ABC DEF" BFS
3, 17, 8, 10

2.
java FifteenProblem " 12356749AB8DEFC" BFS
6, 203, 96, 108


DFS Output:
    Time Complexity: O(n^m) m = size of tree n = node
1.
java FifteenProblem "123456789ABC DEF" DFS
3, 4, 4, 4

2.
java FifteenProblem " 12356749AB8DEFC" DFS
6, 7, 7, 7


GBFS h1 Output:
    Time Complexity:O(b^m)
1.
java FifteenProblem "123456789A BCDEF" GBFS h1
94, 2027, 977, 1051

2.
java FifteenProblem "74E12 3896DFA5BC" GBFS h1
398, 25274, 12343, 12932


GBFS h2 Output:
    Time Complexity:O(b^m)
1.
java FifteenProblem "123456789A BCDEF" GBFS h2
94, 2027, 977, 1051

2.
java FifteenProblem "74E12 3896DFA5BC" GBFS h2
344, 49187, 25488, 23700

AStar h1 Output:
    Time Complexity:O(b^d)
1.
java FifteenProblem "123456789 ABCDEF" AStar h1
21, 41525, 20655, 20871

2.
java FifteenProblem "123456789A BCDEF" AStar h1
20, 28706, 14222, 14485

AStar h2 Output:
    Time Complexity:O(b^d)
1.
java FifteenProblem "123456789 ABCDEF" AStar h2
21, 4847, 2458, 2390

2.
java FifteenProblem "123456789A BCDEF" AStar h2
20, 2934, 1493, 1442


DLS Output:O
    Time Complexity:O(b^d)
1.
java FifteenProblem " 12356749AB8DEFC" DLS 9
6, 7, 7, 7

2.
java FifteenProblem "123456789ABC DEF" DLS 9
3, 4, 4, 4