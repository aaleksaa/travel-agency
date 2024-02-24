package implementation.client;

import implementation.general.Validator;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputControl;
import models.entities.Arrangement;
import models.enums.RoomType;
import models.enums.Transport;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The ArrangementViewer class provides methods for viewing and filtering arrangements.
 */
public class ArrangementViewer {
    /**
     * Filters arrangements to display only those currently on offer.
     *
     * @param arrangements the list of arrangements to filter.
     * @return a list of arrangements currently on offer.
     */
    public static List<Arrangement> arrangementsOnOffer(List<Arrangement> arrangements) {
        return arrangements
                .stream()
                .filter(Arrangement::isOnOffer)
                .toList();
    }

    /**
     * Filters arrangements based on various criteria.
     *
     * @param arrangements the list of arrangements to filter.
     * @param price        the price criteria.
     * @param destination  the destination criteria.
     * @param starReview   the star review criteria.
     * @param roomType     the room type criteria.
     * @param transport    the transport criteria.
     * @param tripDate     the trip date criteria.
     * @param arrivalDate  the arrival date criteria.
     * @return a list of filtered arrangements.
     */
    public static List<Arrangement> filterArrangements(List<Arrangement> arrangements, String price, String destination, String starReview, RoomType roomType, Transport transport, LocalDate tripDate, LocalDate arrivalDate) {
        return arrangementsOnOffer(arrangements)
                .stream()
                .filter(arr -> price.isEmpty() || arr.isPriceLower(Double.parseDouble(price)))
                .filter(arr -> destination.isEmpty() || arr.isDestinationMatching(destination))
                .filter(arr -> roomType == null || filterHelperRoomType(arr, roomType))
                .filter(arr -> transport == null || arr.getTransport() == transport)
                .filter(arr -> starReview.isEmpty() || filterHelperStarReview(arr, starReview))
                .filter(arr -> tripDate == null || arr.isTripScheduledAfter(tripDate))
                .filter(arr -> arrivalDate == null || arr.isArrivalPlannedBefore(arrivalDate))
                .toList();
    }

    /**
     * Helper method to filter arrangements based on room type.
     *
     * @param arrangement the arrangement to be filtered.
     * @param roomType    the room type criteria.
     * @return true if the arrangement's accommodation matches the given room type, false otherwise.
     */
    private static boolean filterHelperRoomType(Arrangement arrangement, RoomType roomType) {
        return Optional
                .ofNullable(arrangement.getAccommodation())
                .map(accommodation -> accommodation.getRoomType() == roomType)
                .orElse(false);
    }

    /**
     * Helper method to filter arrangements based on star review.
     *
     * @param arrangement the arrangement to be filtered.
     * @param starReview  the star review criteria.
     * @return true if the arrangement's accommodation has the specified star review, false otherwise.
     */
    private static boolean filterHelperStarReview(Arrangement arrangement, String starReview) {
        return Optional
                .ofNullable(arrangement.getAccommodation())
                .map(accommodation -> accommodation.getStarReview() == Integer.parseInt(starReview))
                .orElse(false);
    }

    /**
     * Sorts the ListView based on specified criteria and sort order.
     *
     * @param lv       the ListView to be sorted.
     * @param criteria the sorting criteria.
     * @param sort     the sort order (Ascending or Descending).
     */
    public static void sortListView(ListView<Arrangement> lv, int criteria, String sort) {
        if (criteria == 1 && sort.equals("Ascending"))
            lv.getItems().sort(Arrangement.tripDateAscending);
        else if (criteria == 1 && sort.equals("Descending"))
            lv.getItems().sort(Arrangement.tripDateDescending);
        else if (criteria == 2 && sort.equals("Ascending"))
            lv.getItems().sort(Arrangement.priceAscending);
        else
            lv.getItems().sort(Arrangement.priceDescending);
    }

    /**
     * Resets the ListView and input fields to their default states.
     *
     * @param arrangements the list of arrangements.
     * @param lv           the ListView of arrangements.
     * @param inputs       the array of text input controls.
     * @param dp1          the date picker for trip date.
     * @param dp2          the date picker for arrival date.
     * @param cb1          the choice box for room type.
     * @param cb2          the choice box for transport.
     */
    public static void reset(List<Arrangement> arrangements, ListView<Arrangement> lv, TextInputControl[] inputs, DatePicker dp1, DatePicker dp2, ChoiceBox<String> cb1, ChoiceBox<String> cb2) {
        lv.getItems().setAll(arrangementsOnOffer(arrangements));
        Validator.resetInputs(inputs);

        dp1.setValue(null);
        dp2.setValue(null);

        cb1.getSelectionModel().selectFirst();
        cb2.getSelectionModel().selectFirst();
    }
}
