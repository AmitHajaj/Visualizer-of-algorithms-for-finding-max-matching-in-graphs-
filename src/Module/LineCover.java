import javax.swing.text.MutableAttributeSet;
import java.util.HashMap;

public class LineCover { // kfir's comment
    Graph g;
    Match lc;
    Match gse;
    Node[] na = { new Node(), new Node(), new Node(), new Node()};
    /* g.Nodes().size() */
    // maybe we can remove MAY and then delete the enum and add a boolean array
    enum mustBe{ MUST, MAY, MUSTNT };

    Match lowestLineCover(HashMap<Edge, mustBe> map, Edge[] ea, int i) { // ea edge array
        if(i != ea.length){
            Match without = new Match();
            for(Edge edge : ea){
                if(map.get(edge) == mustBe.MUST){
                    without.add(edge);
                }
            }
            if(ifReallyCovers(without)){
                return without;
            }

            Match with = without;
            with.add(ea[i]);

            if(ifReallyCovers(with)){
                return with;
            }
            else return new Match();
        }

        map.put(ea[i], mustBe.MUSTNT);
        Match without = lowestLineCover(map, ea, i+1);
        map.put(ea[i], mustBe.MUST);
        Match with = lowestLineCover(map, ea, i+1);

        if(i== ea.length()) {
            if (ifReallyCovers(without)) {
                return without;
            } else if (ifReallyCovers(with)) {
                return with;
            } else return new Match();
        }
    }

    Match lowestLineCover() {
        Match lowestLC = g.getAE(); // all the edges in the graph
        Match currLC = lowestLC;
        Edge[g.getAE().size()] ea; // edges array
        HashMap<Edge, mustBe> map = new HashMap<>();
        for (Edge e : g.getAE()) {
            ea[++i] = e;
            map.put(e, mustBe.MAY);
        }
        return lowestLineCover(map, ea, 0);
    }

    boolean ifReallyCovers(Match m){
        if (m.size() == 0 && g.getAN().size() == 0) return true; // AN all nodes
        boolean[] ba = {false, false, false, false};
        int counter = 0;
        for(Edge e : m){
            // i didn't count because we may count the same node twice and miss another node
            if(!ba[e.getDest()]) {
                ba[e.getDest()] = true;
                counter++;
            }
            if(!ba[e.getSrc()]) {
                ba[e.getSrc()] = true;
                counter++;
            }
        }
        for (int i = 0; i< ba.length; i++){
            if(!ba[i]) return false;
        }
        return true;
    }
}

