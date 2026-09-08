package thomas.place;

import java.util.ArrayList;
import java.util.stream.Collectors;

/** Stores saved places and provides place-list operations. */
public class PlaceList {
    private final ArrayList<Place> places;

    /** Creates a place list from existing places. */
    public PlaceList(ArrayList<Place> places) {
        this.places = places;
    }

    /** Creates an empty place list. */
    public PlaceList() {
        this(new ArrayList<>());
    }

    public void add(Place place) {
        places.add(place);
    }

    public Place get(int index) {
        return places.get(index);
    }

    public Place delete(int index) {
        return places.remove(index);
    }

    public int size() {
        return places.size();
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    /** Returns places whose names contain the keyword, ignoring case. */
    public ArrayList<Place> findByName(String keyword) {
        String normalizedKeyword = keyword.toLowerCase();
        return places.stream().filter(place -> place.getName().toLowerCase().contains(normalizedKeyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /** Returns the current one-based index of a place. */
    public int getOneBasedIndex(Place place) {
        return places.indexOf(place) + 1;
    }
}
