package implementation.client;

import com.mysql.cj.xdevapi.DeleteStatementImpl;
import javafx.scene.control.ListView;
import models.entities.Arrangement;
import models.enums.RoomType;
import models.enums.Transport;

import java.util.List;
import java.util.Optional;

public class ArrangementViewer {
    public static List<Arrangement> arrangementsOnOffer(List<Arrangement> arrangements) {
        return arrangements
                .stream()
                .filter(Arrangement::isOnOffer)
                .toList();
    }

    public static List<Arrangement> filterArrangements(List<Arrangement> arrangements, String price, String destination, String starReview, RoomType roomType, Transport transport, String tripDate, String arrivalDate) {
        return arrangementsOnOffer(arrangements)
                .stream()
                .filter(arr -> price.isEmpty() || arr.isPriceLower(Double.parseDouble(price)))
                .filter(arr -> destination.isEmpty() || arr.isDestinationMatching(destination))
                .filter(arr -> roomType == null || filterHelperRoomType(arr, roomType))
                .filter(arr -> transport == null || arr.getTransport() == transport)
                .filter(arr -> starReview.isEmpty() || filterHelperStarReview(arr, starReview))
                .toList();
    }

    private static boolean filterHelperRoomType(Arrangement arrangement, RoomType roomType) {
        return Optional
                .ofNullable(arrangement.getAccommodation())
                .map(accommodation -> accommodation.getRoomType() == roomType)
                .orElse(false);
    }

    private static boolean filterHelperStarReview(Arrangement arrangement, String starReview) {
        return Optional
                .ofNullable(arrangement.getAccommodation())
                .map(accommodation -> accommodation.getStarReview() == Integer.parseInt(starReview))
                .orElse(false);
    }

    public static void sortListView(ListView<Arrangement> lv, int criteria, String sort) {
        if (criteria == 1 && sort.equals("Ascending"))
            lv.getItems().sort(Arrangement.tripDateAscending);
        else if (criteria == 1 && sort.equals("Descending"))
            lv.getItems().sort(Arrangement.tripDateDescending);
        else if (criteria == 2 && sort.equals("Ascending"))
            lv.getItems().sort(Arrangement.priceAscending);
        else if (criteria == 2 && sort.equals("Descending"))
            lv.getItems().sort(Arrangement.priceDescending);
    }
}
