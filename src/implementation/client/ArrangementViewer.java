package implementation.client;

import models.arrangement.Arrangement;

import java.util.List;

public class ArrangementViewer {
    public static List<Arrangement> arrangementsOnOffer(List<Arrangement> arrangements) {
        return arrangements
                .stream()
                .filter(Arrangement::isOnOffer)
                .toList();
    }
}
