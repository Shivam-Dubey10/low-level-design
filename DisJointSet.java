import java.util.*;

class DisJointSet {
    List<Integer> parent = new ArrayList<>();
    List<Integer> rank = new ArrayList<>();
    List<Integer> size = new ArrayList<>();

    public DisJointSet(int n) {
        for(int i=0;i<=n;i++) {
            parent.add(i);
            rank.add(0);
            size.add(1);
        }
    }

    public int findParent(int node) {
        if(node == parent.get(node)) {
            return node;
        } 
        int nodeParent = findParent(parent.get(node));
        parent.set(node, nodeParent);
        return parent.get(node);
    }

    public void unionByRank(int u, int v) {
        int up_u = findParent(u);
        int up_v = findParent(V);
        IF(up_u == up_v) return;
        if(rank.get(u) < rank.get(v)) {
            parent.set(up_u, up_v);
        } else if(rank.get(v) < rank.get(u)) {
            parent.set(up_v, up_u);
        } else {
            parent.set(up_v, up_u);
            int rankUpU = rank.get(up_u);
            rank.set(up_u, rankUpU+1);
        }
    }

    public void unionBySize(int u, int v) {
        int up_u = findParent(u);
        int up_v = findParent(v);
        if(up_u==up_v) {
            return;
        }
        if(size.get(u) > size.get(v)) {
            parent.set(up_v, up_u);
            size.set(u, size.get(u)+size.get(v));
        }
        else {
            parent.set(up_u, up_v);
            size.set(v, size.get(v)+size.get(u));
        }
    } 

}