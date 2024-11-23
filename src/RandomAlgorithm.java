import java.util.Collections;
import java.util.List;

public class RandomAlgorithm implements Algorithm{
    @Override
    public void step(Game game, Penguin penguin) {
        List<Tile> neighbours=game.getNeighbours(penguin.getPos().getXindex(),penguin.getPos().getYindex());
        if(Collections.frequency(neighbours,null)>0){
            game.setGameState(GameState.LOST);
            return;
        }
        if(freeNeighbours(neighbours)==0){
            game.setGameState(GameState.WON);
            return;
        }
        if(penguin.getDir()==Direction.WEST){
            if(stepForwardCount(penguin.getDir(), neighbours)==0){
                Tile tmp=getRandomNeighbour(neighbours,0, 1, 2);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }else{
                Tile tmp=getRandomNeighbour(neighbours,3,4, 5);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }
        }else if(penguin.getDir()==Direction.EAST){
            if(stepForwardCount(penguin.getDir(), neighbours)==0){
                Tile tmp=getRandomNeighbour(neighbours,3,4, 5);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }else{
                Tile tmp=getRandomNeighbour(neighbours,0,1, 2);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }
        }else if(penguin.getDir()==Direction.NORTH_EAST){
            if(stepForwardCount(penguin.getDir(), neighbours)==0){
                Tile tmp=getRandomNeighbour(neighbours,2, 3, 4);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }else{
                Tile tmp=getRandomNeighbour(neighbours,5,0, 1);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }
        }else if(penguin.getDir()==Direction.NORTH_WEST){
            if(stepForwardCount(penguin.getDir(), neighbours)==0){
                Tile tmp=getRandomNeighbour(neighbours,1, 2, 3);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }else{
                Tile tmp=getRandomNeighbour(neighbours,4,5, 0);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }


        }else if(penguin.getDir()==Direction.SOUTH_EAST){
            if(stepForwardCount(penguin.getDir(), neighbours)==0){
                Tile tmp=getRandomNeighbour(neighbours,4, 5, 0);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }else{
                Tile tmp=getRandomNeighbour(neighbours,1,2, 3);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }

        }else if(penguin.getDir()==Direction.SOUTH_WEST){
            if(stepForwardCount(penguin.getDir(), neighbours)==0){
                Tile tmp=getRandomNeighbour(neighbours,5, 0, 1);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }else{
                Tile tmp=getRandomNeighbour(neighbours,2,3, 4);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }
        }
    }

    private int stepForwardCount(Direction dir, List<Tile> neighbours){
        if(dir==Direction.WEST){
            return (neighbours.get(3).getState()==State.FIRST?1:0) + (neighbours.get(4).getState()==State.FIRST?1:0) + (neighbours.get(5).getState()==State.FIRST?1:0);
        }else if(dir==Direction.EAST){
            return (neighbours.get(0).getState()==State.FIRST?1:0) + (neighbours.get(1).getState()==State.FIRST?1:0) + (neighbours.get(2).getState()==State.FIRST?1:0);
        }else if(dir==Direction.NORTH_EAST){
            return (neighbours.get(5).getState()==State.FIRST?1:0) + (neighbours.get(0).getState()==State.FIRST?1:0) + (neighbours.get(1).getState()==State.FIRST?1:0);
        }else if(dir==Direction.NORTH_WEST){
            return (neighbours.get(4).getState()==State.FIRST?1:0) + (neighbours.get(5).getState()==State.FIRST?1:0) + (neighbours.get(0).getState()==State.FIRST?1:0);
        }else if(dir==Direction.SOUTH_EAST){
            return (neighbours.get(1).getState()==State.FIRST?1:0) + (neighbours.get(2).getState()==State.FIRST?1:0) + (neighbours.get(3).getState()==State.FIRST?1:0);
        }else if(dir==Direction.SOUTH_WEST){
            return (neighbours.get(2).getState()==State.FIRST?1:0) + (neighbours.get(3).getState()==State.FIRST?1:0) + (neighbours.get(4).getState()==State.FIRST?1:0);
        }
        return 0;
    }

    private int freeNeighbours(List<Tile> neighbours){
        int count=0;
        for(Tile t:neighbours){
            if(t.getState()==State.FIRST){
                count++;
            }
        }
        return count;
    }

    private Tile getRandomNeighbour(List<Tile> neighbours, int... values){
        Tile tmp=neighbours.get(values[Game.generateRandom(0,values.length-1)]);
        while(tmp.getState()!=State.FIRST){
            tmp=neighbours.get(values[Game.generateRandom(0,values.length-1)]);
        }
        return tmp;
    }

    private Direction getNewDirection(Tile nextPos, List<Tile> neighbours){
        if(neighbours.indexOf(nextPos)==0){
            return Direction.NORTH_EAST;
        }else if(neighbours.indexOf(nextPos)==1){
            return Direction.NORTH_EAST;
        }else if(neighbours.indexOf(nextPos)==2){
            return Direction.SOUTH_EAST;
        }else if(neighbours.indexOf(nextPos)==3){
            return Direction.SOUTH_WEST;
        }else if(neighbours.indexOf(nextPos)==4){
            return Direction.WEST;
        }else{
            return Direction.NORTH_WEST;
        }
    }
}

