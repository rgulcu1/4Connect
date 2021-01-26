# 4Connect

In our Connect-Four Game we did used 3 different heuristic methods and a ply number that can be change for AI player. We used minimax algorithm with alpha-beta pruning.

How to run:
java demoConnect4 -g “gameType” -p1 “heuristicFunction” “depth" -p2 “heuristicFunction” “depth”

gameType: 1 for Human vs Human , 2 for Human vs Ai and 3 for Ai vs Ai 
heuristicFunction : h1 for heuristic1 , h2 for heuristic3 and h3 for heuristic3 
depth: Integer between 1 and 8

Example Runs:

Human vs Human -> java demoConnect4 -g 1
Human vs AI ->java demoConnect4 -g 2 -p2 h2 8
AI vs AI -> java demoConnect4 -g 3 -p1 h3 8 -p2 h2 8

P.S : You should press enter to perform AI movement.
