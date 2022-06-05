import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static int maxNodes = 4;
    public static int[] parentNodes = new int[maxNodes];

    // Find set of vertex i
    public static int find(int i)
    {
        while (parentNodes[i] != i)
            i = parentNodes[i];
        return i;
    }

    public static void union1(int i, int j)
    {
        int a = find(i);
        int b = find(j);
        parentNodes[a] = b;
    }

    public static void kruskalMST(int[][] cost, int n)
    {
        int mincost = 0; // Cost of min MST.

        // Initialize sets of disjoint sets.
        for (int i = 0; i < n; i++)
            parentNodes[i] = i;

        int edge_count = 0;
        while (edge_count < n - 1) {
            int min = Integer.MAX_VALUE, a = -1, b = -1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (find(i) != find(j) && cost[i][j] < min) {
                        min = cost[i][j];
                        a = i;
                        b = j;
                    }
                }
            }

            union1(a, b);
            System.out.printf("Edge %d:(%d, %d) cost: \n",
                    edge_count++, a, b, min);
            mincost += min;

        }

    }

    public static int minKey(int key[], boolean mstSet[])
    {
        int min = 100000, min_index = 0;

        for (int v = 0; v < maxNodes; v++)
            if (mstSet[v] == false && key[v] < min)
            {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    public static void printMST(int parent[], int[][] graph,int n)
    {
        System.out.println("Edge weight:");

        for (int i = 1; i < n; i++)
            System.out.println(parentNodes[i] + " - " + i + " \t" + graph[i][parent[i]] );
    }

    public static void primMST(int[][] graph,int n)
    {
        int[] parent = new int[maxNodes];
        int[] key = new int[maxNodes];
        boolean[] mstSet = new boolean[maxNodes];

        // Initialize all keys as INFINITE
        for (int i = 0; i < maxNodes; i++)
        {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;
        parent[0] = -1; // First node is always root of MST

        for (int count = 0; count < maxNodes - 1; count++)
        {
            int u = minKey(key, mstSet);

            mstSet[u] = true;

            for (int v = 0; v < maxNodes; v++)
                if (graph[u][v]!=0 && mstSet[v] == false && graph[u][v] < key[v])
                {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }

        // print the constructed MST
        printMST(parent, graph,n);
    }

    public static void main(String[] args) throws IOException {

        int[][] graph = new int[maxNodes*maxNodes][maxNodes*maxNodes];

        String[] nodeNames = new String[maxNodes];

        for (int i = 0;i < maxNodes;i++)
        {
            nodeNames[i] = String.valueOf(i);
        }

        for (int i = 0;i < maxNodes;i++) {
            int x, y, z;
            x = i;
            y = i+1;
            z = i+2;

            graph[x][y] = z;
            graph[y][x] = z;
        }

        System.out.println("Prims: ");
        primMST(graph,maxNodes);
        System.out.println("Kruskal: ");
        kruskalMST(graph,maxNodes);
    }
}