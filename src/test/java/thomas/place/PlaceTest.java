package thomas.place;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import thomas.ThomasException;

class PlaceTest {
    @Test
    void update_optionalFieldsWithNone_clearsFields() throws Exception {
        Place place = new Place("Sushi", "Restaurant", "Town", 4, "12", Place.parseDate("2026-09-08"), "Nice");

        place.update("visited", "none");
        place.update("note", "none");

        assertNull(place.getVisitedDate());
        assertNull(place.getNote());
    }

    @Test
    void constructor_priceAndRatingValidation_rejectsInvalidValues() {
        assertThrows(ThomasException.class, () ->
                new Place("Sushi", "Restaurant", "Town", 6, "12", null, null));
        assertThrows(ThomasException.class, () ->
                new Place("Sushi", "Restaurant", "Town", 4, "12.345", null, null));
    }

    @Test
    void constructor_validPrice_normalizesToTwoDecimalPlaces() throws Exception {
        Place place = new Place("Sushi", "Restaurant", "Town", 4, "12", null, null);

        assertEquals("12.00", place.getPrice());
    }
}
