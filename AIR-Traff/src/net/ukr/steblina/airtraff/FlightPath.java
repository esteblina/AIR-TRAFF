package net.ukr.steblina.airtraff;

public enum FlightPath {
	N("N"),
	S("S"),
	W("W"),
	E("E"),
	NW("NW"),
	SW("SW"),
	NE("NE"),
	SE("SE");

    private final String text;

    private FlightPath(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
