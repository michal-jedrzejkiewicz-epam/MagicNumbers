package Engine;

public enum FileExtension {
    UNKNOWN,
    TXT,
    JPG,
    JPEG,
    JPEG_LONG,
    GIF,
    PDF,
    RAR,
    PCAP;

    public String getLowerString() {
        return toString().toLowerCase();
    }
}
