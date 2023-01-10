package luongdev.switchconfig.domain.extension;

public enum ExtensionType {

    GROUP("GROUP"),
    DIRECTORY("DIRECTORY"),
    DIALPLAN("DIALPLAN"),
    CALLCENTER("CALLCENTER");

    final String value;

    ExtensionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
