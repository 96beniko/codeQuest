import java.util.*;
import java.util.stream.Collectors;


public class PoisonSwamp {
    public static void main(String args[]) {

        // map.txt (replace [ => {, ] => })
        int input[][] = {
                {-1, 1, 1, -1, -1, 1, 1, -1, -1, 1}
                , {1, 0, 1, -999, -1, -1, -1, -1, -1, -1}
                , {-1, -1, -1, 1, 1, -1, 1, -1, 1, 1}
                , {1, -999, -1, -1, 1, -1, 1, -1, 1, -1}
                , {-1, 1, 1, -1, -1, -1, -1, -999, 1, -1}
                , {1, -1, 1, -1, -1, 1, -1, 1, -1, 1}
                , {-1, -999, -1, -1, -1, 1, -1, -1, -1, 1}
                , {-1, 1, -1, 1, 1, -1, 1, -1, -1, -1}
                , {-1, 1, -1, -1, -1, 1, 1, -1, 1000, 1}
                , {-1, -1, 1, 1, -1, -1, -1, 1, -1, -1}};

        // map.txt (replace [ => {, ] => })
//        int input[][] = {
//                {1, 1, 1, -1}
//                , {1, 0, 1, -999}
//                , {-1, -1, -1, 1}
//                , {1, -999, -1, 1000}
//        };

//        final int START_X = 1;
//        final int START_Y = 1;
//        final int GOAL_X = 3;
//        final int GOAL_Y = 3;
//        final int MAX_SIZE_X = 4;
//        final int MAX_SIZE_Y = 4;
//        final int DEATH = -999;

        final int START_X = 1;
        final int START_Y = 1;
        final int GOAL_X = 8;
        final int GOAL_Y = 8;
        final int MAX_SIZE_X = 10;
        final int MAX_SIZE_Y = 10;
        final int DEATH = -999;

        // up, right, down, left
        final int dx[] = {0, 1, 0, -1};
        final int dy[] = {1, 0, -1, 0};
        
        int nowAreaPoint = 0;

        List<Status> statusList = new ArrayList<>();
        List<Status> candidateList = new ArrayList<>();
        
        Status currentStatus = new Status(START_X,START_Y);
        statusList.add(currentStatus);

        while(statusList.size() != 0) {
            for (Status status : statusList) {

                int x = status.position.x;
                int y = status.position.y;

                for (int i = 0; i < 4; i++) {
                    int nextX = x + dx[i];
                    int nextY = y + dy[i];

                    // Out of range
                    if (nextX < 0 || nextY < 0 || MAX_SIZE_X <= nextX || MAX_SIZE_Y <= nextY) {
                        continue;
                    }

                    // Impassable
                    nowAreaPoint = input[nextY][nextX];
                    if (nowAreaPoint == DEATH) {
                        continue;
                    }

                    if (nowAreaPoint == input[GOAL_Y][GOAL_X]) {
                        if (status.getHp() >= 50) {
                            printResult(status);
                            return;
                        }
                        continue;
                    }

                    // Passed
                    List<Position> histList = status.hist;
                    boolean isPassed = false;
                    for (Position h : histList) {
                        if (h.x == nextX && h.y == nextY) {
                            isPassed = true;
                        }
                    }

                    if (!isPassed) {
                        Status s = status.clone();
                        s.setPostion(nextX, nextY);
                        s.setHp(s.getHp() + nowAreaPoint);
                        candidateList.add(s);
                    }

                }

                
            }

            statusList.clear();
            statusList.addAll(
                    candidateList.stream().sorted(
                            Comparator.comparing(Status::getHp).reversed()
                    ).limit(1000000).collect(Collectors.toList())
            );
            candidateList.clear();
        }    

    }

    private static void printResult(Status status) {
        int beforeX = -333;
        int beforeY = -333;
        for (Position p : status.hist) {
            // System.out.println("x = " + p.x + ", y = " + p.y);
            if(beforeX == -333 && beforeY == -333){
                beforeX = p.x;
                beforeY = p.y;
            }
            else{
                if(beforeX == p.x && beforeY > p.y){
                    System.out.println("↑");
                }
                else if(beforeX == p.x && beforeY < p.y){
                    System.out.println("↓");
                }
                else if(beforeX > p.x && beforeY == p.y){
                    System.out.println("←");
                }
                else if(beforeX < p.x && beforeY == p.y){
                    System.out.println("→");
                }
                beforeX = p.x;
                beforeY = p.y;
            }
        }
    }
}
