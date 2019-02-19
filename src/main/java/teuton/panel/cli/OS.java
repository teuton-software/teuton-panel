package teuton.panel.cli;

public enum OS {
	WINDOWS("windows"),
	LINUX("linux"),
	MACOSX("macosx");

    private final String os;

    OS(final String os) {
        this.os = os;
    }

    @Override
    public String toString() {
        return os;
    }
    
}
