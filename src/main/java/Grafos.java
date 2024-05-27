public class Grafos {
    Nodo[] listaAdyacencia;
    char[] relacionListaAdyacencia;
    int[][] matrizAdyacencia;
    int[][] matrizIncidencia;
    int[] visitado;


    public Grafos(int numVertices, int numAristas){
        listaAdyacencia = new Nodo[numVertices];
        relacionListaAdyacencia = new char[numVertices];
        matrizAdyacencia = new int[numVertices][numVertices];
        matrizIncidencia = new int[numVertices][numAristas];
        visitado = new int[numVertices];
    }

    public void CrearGrafo(String vertices, String aristas){
        LlenarListaAdyacencia(vertices);
        LlenarAristas(aristas);
    }

    private void LlenarAristas(String aristas) {
        int caminoIda = 1, caminoVuelta = 1;
        for (int i = 0; i < aristas.length(); i++) {
            if (aristas.charAt(i) != ' ' && aristas.charAt(i) != ',' && aristas.charAt(i) != ';' && !Character.isDigit(aristas.charAt(i))) {
                char origen = aristas.charAt(i);
                char destino = aristas.charAt(i + 2);
                int peso = ConseguirPeso(aristas, i + 4);
                for (int aux = 0; aux < relacionListaAdyacencia.length; aux++) {
                    /*
                    Siempre va a entrar a estos dos if, lo cual asegura la correcta asignacion del camino de ida o vuelta
                    Implica que los caminos de ida o vuelta sean los mismos al final de la iteracion
                     */
                    if (relacionListaAdyacencia[aux] == origen) {
                        EnlazarListaAdyacencia(caminoIda, destino, peso, aux);
                        caminoIda++;
                    }
                    // Agregar la arista en la dirección opuesta para grafos no dirigidos
                    if (relacionListaAdyacencia[aux] == destino) {
                        EnlazarListaAdyacencia(caminoVuelta, origen, peso, aux);
                        caminoVuelta++;
                    }
                }
                i = i + 4;
            }
        }
    }

    private void EnlazarListaAdyacencia(int camino, char origen, int peso, int aux) {
        Nodo nuevo;
        if (listaAdyacencia[aux] == null) {
            nuevo = new Nodo(origen, peso, camino, null);
            listaAdyacencia[aux] = nuevo;
        } else {
            Nodo ant = NodoFinalLista(listaAdyacencia[aux]);
            nuevo = new Nodo(origen, peso, camino, null);
            ant.setLiga(nuevo);
        }
    }

    private int ConseguirPeso(String aristas, int i) {
        return getPesoOrCamino(aristas, i);
    }

    private Nodo NodoFinalLista(Nodo nodo) {
        Nodo p = nodo;
        while (p.getLiga() != null) {
            p = p.getLiga();
        }
        return p;
    }

    private void LlenarListaAdyacencia(String vertices) {
        int index = 0;
        for (int i = 0; i < vertices.length(); i++) {
            if (vertices.charAt(i) != ',' && vertices.charAt(i) != ' '){
                relacionListaAdyacencia[index++] = vertices.charAt(i);
            }
        }
    }

    public void ImprimirListaAdyacencia() {
        int i = 0;
        for (Nodo n : listaAdyacencia) {
            System.out.print("\n" + relacionListaAdyacencia[i++] + ((n == null) ? " /" : " -> "));
            Nodo p = n;
            while (p != null) {
                System.out.print(p.getVertice() + " (" + p.getCamino() + ")" + " (" + p.getPeso() + ")" + ((p.getLiga() == null) ? " /" : " -> "));
                p = p.getLiga();
            }
        }
        System.out.print("\n");
    }

    public void LlenarMatrizAdyacencia() {
        int tam = relacionListaAdyacencia.length;
        int[][] Matriz = new int[tam][tam];
        char aux;
        Nodo p;
        for (int i = 0; i < tam; i++) {
            p = listaAdyacencia[i];
            while (p != null) {
                aux = p.getVertice();
                for (int j = 0; j < tam; j++) {
                    if (relacionListaAdyacencia[j] == aux) {
                        Matriz[i][j] ++;
                        break;
                    }
                }
                p = p.getLiga();
            }
        }
        matrizAdyacencia = Matriz;
    }


    public void ImprimirMatrizAdyacencia() {
        for (char c : relacionListaAdyacencia) {
            System.out.print("  " + c);
        }
        System.out.print("\n");
        for (int i = 0; i < relacionListaAdyacencia.length; i++) {
            System.out.print(relacionListaAdyacencia[i] + "  ");
            for (int j = 0; j < relacionListaAdyacencia.length; j++) {
                System.out.print(matrizAdyacencia[i][j] + "  ");
            }
            System.out.print("\n");
        }
    }

    public void LlenarMatrizIncidencia() {
        int tamfil = relacionListaAdyacencia.length;
        int tamcol = matrizIncidencia[0].length;
        int[][] Matriz = new int[tamfil][tamcol];
        Nodo p;
        for (int i = 0; i < tamfil; i++) {
            p = listaAdyacencia[i];
            for (int j = 0; j < tamcol; j++) {
                while (p != null) {
                    Matriz[i][p.getCamino()-1] ++;
                    p = p.getLiga();
                }
            }
        }
        matrizIncidencia = Matriz;
    }

    public void ImprimirMatrizIncidencia() {
        for (int i = 1; i <= matrizIncidencia[0].length; i++) {
            System.out.print("  " + i);
        }
        System.out.print("\n");
        for (int i = 0; i < matrizIncidencia.length; i++) {
            System.out.print(relacionListaAdyacencia[i] + "  ");
            for (int j = 0; j < matrizIncidencia[0].length; j++) {
                System.out.print(matrizIncidencia[i][j] + "  ");
            }
            System.out.print("\n");
        }
    }

    public void DFS(char v) {
        Nodo p;
        char w;
        int num = BuscarIndice(v);
        visitado[num] = 1;
        System.out.print(v + " ");
        p = listaAdyacencia[num];
        while (p != null) {
            w = p.getVertice();
            if (visitado[BuscarIndice(w)] == 0) {
                DFS(w);
            }
            p = p.getLiga();
        }
    }

    //Busca el indice en la relacion de la lista Adyacencia del vertice con char V, devuelve el indice si lo encuentra, si no devuelve -1
    public int BuscarIndice(char v) {
        for (int i = 0; i < relacionListaAdyacencia.length; i++) {
            if (relacionListaAdyacencia[i] == v) {
                return i;
            }
        }
        return -1;
    }

    public void BFS(char v) {
        int num = BuscarIndice(v);
        if (num == -1) {
            return;
        }
        boolean[] visitado = new boolean[relacionListaAdyacencia.length];
        char[] array = new char[relacionListaAdyacencia.length];
        visitado[num] = true;
        array[0] = v;
        int index = 0;
        int end = 1; //end es el indice del ultimo elemento del array
        while (index < end) {
            char nodo = array[index];
            System.out.print(nodo + " ");
            Nodo p = listaAdyacencia[BuscarIndice(nodo)];
            while (p != null) {
                char vecino = p.getVertice();
                int indiceVecino = BuscarIndice(vecino);
                if (!visitado[indiceVecino]) {
                    array[end] = vecino;
                    end++;
                    visitado[indiceVecino] = true;
                }
                p = p.getLiga();
            }
            index++;
        }
    }

    public void DistanciaMinima(char origen) {
        int[] distancias = new int[relacionListaAdyacencia.length];
        boolean[] visitado = new boolean[relacionListaAdyacencia.length];
        int[] predecesor = new int[relacionListaAdyacencia.length];
        for (int i = 0; i < relacionListaAdyacencia.length; i++) {
            distancias[i] = Integer.MAX_VALUE;
            predecesor[i] = -1;
        }
        int nodoOrigen = BuscarIndice(origen);
        distancias[nodoOrigen] = 0;
        for (int i = 0; i < relacionListaAdyacencia.length; i++) {
            int nodoActual = nodoMinimaDistancia(distancias, visitado);
            visitado[nodoActual] = true;
            Nodo p = listaAdyacencia[nodoActual];
            while (p != null) {
                int indiceVecino = BuscarIndice(p.getVertice());
                int distanciaAlternativa = distancias[nodoActual] + p.getPeso();
                if (distanciaAlternativa < distancias[indiceVecino]) {
                    distancias[indiceVecino] = distanciaAlternativa;
                    predecesor[indiceVecino] = nodoActual;
                }
                p = p.getLiga();
            }
        }
        imprimirDistanciasMinimas(distancias, predecesor, origen);
    }

    private int nodoMinimaDistancia(int[] distancias, boolean[] visitado) {
        int minimaDistancia = Integer.MAX_VALUE;
        int nodoMinimaDistancia = -1;
        for (int i = 0; i < relacionListaAdyacencia.length; i++) {
            if (!visitado[i] && distancias[i] < minimaDistancia) {
                minimaDistancia = distancias[i];
                nodoMinimaDistancia = i;
            }
        }
        return nodoMinimaDistancia;
    }

    private void imprimirDistanciasMinimas(int[] distancias, int[] predecesor, char origen) {
        System.out.println("Distancias mínimas desde el nodo " + origen + ":");
        for (int i = 0; i < relacionListaAdyacencia.length; i++) {
            if (distancias[i] == Integer.MAX_VALUE) {
                System.out.println(relacionListaAdyacencia[i] + " es inalcanzable desde " + origen);
            }
            else {
                System.out.print("Distancia a " + relacionListaAdyacencia[i] + ": " + distancias[i]);
                System.out.print(", camino: " + relacionListaAdyacencia[i]);
                int predecesorNodo = predecesor[i];
                while (predecesorNodo != -1) {
                    System.out.print(" <- " + relacionListaAdyacencia[predecesorNodo]);
                    predecesorNodo = predecesor[predecesorNodo];
                }
                System.out.println();
            }
        }
    }

    public boolean VerticeInexistente(char nodo) {
        for (char c : relacionListaAdyacencia) {
            if (c == nodo) {
                return false;
            }
        }
        return true;
    }

    public void AgregarVertice(char nuevoVertice) {
        int tam = relacionListaAdyacencia.length+1;
        Nodo[] nuevaListaNodos = new Nodo[tam];
        char[] nuevaLista = new char[tam];
        System.arraycopy(relacionListaAdyacencia, 0, nuevaLista, 0, relacionListaAdyacencia.length);
        nuevaLista[tam-1] = nuevoVertice;
        relacionListaAdyacencia = nuevaLista;
        System.arraycopy(listaAdyacencia, 0, nuevaListaNodos, 0, listaAdyacencia.length);
        nuevaListaNodos[tam-1] = null;
        listaAdyacencia = nuevaListaNodos;
    }

    public void AgregarArista(String nuevaArista) {
        int caminoIda = EncontrarUltimoCamino()+1;
        char origen = nuevaArista.charAt(0);
        char destino = nuevaArista.charAt(2);
        int peso = ConseguirPeso(nuevaArista, 4);
        for (int aux = 0; aux < relacionListaAdyacencia.length; aux++) {
            if (relacionListaAdyacencia[aux] == origen) {
                EnlazarListaAdyacencia(caminoIda, destino, peso, aux);
            }
            if (relacionListaAdyacencia[aux] == destino) {
                EnlazarListaAdyacencia(caminoIda, origen, peso, aux);
            }
        }
    }

    private int EncontrarUltimoCamino() {
        int ultimoCamino = listaAdyacencia[0].getCamino();
        for (int i = 1; i < listaAdyacencia.length; i++) {
            Nodo p = listaAdyacencia[i];
            while (p != null) {
                if (p.getCamino() > ultimoCamino) {
                    ultimoCamino = p.getCamino();
                }
                p = p.getLiga();
            }
        }
        return ultimoCamino;
    }


    //Arreglar los eliminar (Revisar y Testear) -> Testear
    public void EliminarVertice(char verticeEliminar) {
        StringBuilder vertices = new StringBuilder();
        StringBuilder aristas = new StringBuilder();
        StringBuilder CaminosPasados = new StringBuilder();
        for (char c : relacionListaAdyacencia) {
            if (c != verticeEliminar) {
                vertices.append(c).append(",");
            }
        }
        for (int i = 0; i < listaAdyacencia.length; i++) {
            Nodo p = listaAdyacencia[i];
            while (p != null) {
                if (p.getVertice() != verticeEliminar && CaminoNoExiste(p.getCamino(), CaminosPasados.toString()) && relacionListaAdyacencia[i] != verticeEliminar) {
                    aristas.append(relacionListaAdyacencia[i]).append(",").append(p.getVertice()).append(",").append(p.getPeso()).append(";");
                    CaminosPasados.append(p.getCamino()).append(",");
                }
                p = p.getLiga();
            }
        }
        ReSizeGlobalVariables(relacionListaAdyacencia.length-1, aristas.toString().split(";").length);
        CrearGrafo(vertices.toString(), aristas.toString());
    }

    private boolean CaminoNoExiste(int camino, String Caminos) {
        int caminoAux;
        StringBuilder aux;
        for (int i = 0; i < Caminos.length(); i++) {
            aux = new StringBuilder();
            while (Character.isDigit(Caminos.charAt(i))) {
                aux.append(Caminos.charAt(i));
                i++;
            }
            caminoAux = Integer.parseInt(aux.toString());
            if (caminoAux == camino) {
                return false;
            }
        }
        return true;
    }

    private void ReSizeGlobalVariables(int numVertices, int numAristas) {
        listaAdyacencia = new Nodo[numVertices];
        relacionListaAdyacencia = new char[numVertices];
        matrizAdyacencia = new int[numVertices][numVertices];
        matrizIncidencia = new int[numVertices][numAristas];
        visitado = new int[numVertices];
    }

    public void EliminarArista(String aristaEliminar) {
        StringBuilder aristas = new StringBuilder();
        StringBuilder vertices = new StringBuilder();
        StringBuilder caminosPasados = new StringBuilder();
        for (int i = 0; i < listaAdyacencia.length; i++) {
            Nodo p = listaAdyacencia[i];
            while (p != null) {
                if ((relacionListaAdyacencia[i] != aristaEliminar.charAt(0) || p.getVertice() != aristaEliminar.charAt(2)) && CaminoNoExiste(p.getCamino(), caminosPasados.toString())
                && ConseguirCamino(aristaEliminar) != p.getCamino()) {
                    aristas.append(relacionListaAdyacencia[i]).append(",").append(p.getVertice()).append(",").append(p.getPeso()).append(";");
                    caminosPasados.append(p.getCamino()).append(",");
                }
                p = p.getLiga();
            }
        }
        for (char c : relacionListaAdyacencia) {
            vertices.append(c).append(",");
        }
        ReSizeGlobalVariables(relacionListaAdyacencia.length, aristas.toString().split(";").length);
        CrearGrafo(vertices.toString(), aristas.toString());
    }

    private int ConseguirCamino(String aristaEliminar) {
        return getPesoOrCamino(aristaEliminar, 4);
    }

    private int getPesoOrCamino(String aristaEliminar, int start) {
        StringBuilder camino = new StringBuilder();
        for (int i = start; i < aristaEliminar.length(); i++) {
            if (aristaEliminar.charAt(i) != ' ' && aristaEliminar.charAt(i) != ',' && aristaEliminar.charAt(i) != ';') {
                camino.append(aristaEliminar.charAt(i));
            }
            else {
                return Integer.parseInt(camino.toString());
            }
        }
        return Integer.parseInt(camino.toString());
    }
}