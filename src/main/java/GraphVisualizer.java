import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javax.swing.*;

public class GraphVisualizer extends JFrame {
    private final Grafos grafo;

    public GraphVisualizer(Grafos grafo) {
        this.grafo = grafo;
    }

    public void init() {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        try {
            // Crear los vértices
            Object[] vertices = new Object[grafo.relacionListaAdyacencia.length];
            for (int i = 0; i < grafo.relacionListaAdyacencia.length; i++) {
                vertices[i] = graph.insertVertex(parent, null, grafo.relacionListaAdyacencia[i], 20, 20, 80, 30);
            }
            // Crear las aristas
            for (int i = 0; i < grafo.listaAdyacencia.length; i++) {
                Nodo p = grafo.listaAdyacencia[i];
                while (p != null) {
                    int j = buscarIndice(p.getVertice(), grafo.relacionListaAdyacencia);
                    if (j != -1) {
                        String valorArista = "P: " + p.getPeso() + ", #: " + p.getCamino(); // Crear la cadena que contiene el peso y el número del camino
                        graph.insertEdge(parent, null, valorArista, vertices[i], vertices[j]);
                    }
                    p = p.getLiga();
                }
            }
        }
        finally {
            graph.getModel().endUpdate();
        }
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        this.getContentPane().add(graphComponent);
        new mxCircleLayout(graph).execute(graph.getDefaultParent());
        this.setSize(800, 600);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private int buscarIndice(char vertice, char[] relacionListaAdyacencia) {
        for (int i = 0; i < relacionListaAdyacencia.length; i++) {
            if (relacionListaAdyacencia[i] == vertice) {
                return i;
            }
        }
        return -1;
    }
}