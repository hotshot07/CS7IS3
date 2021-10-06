import org.immutables.value.Value;

@Value.Immutable
public abstract class FileFormat {
    public abstract int Index();
    public abstract String Title();
    public abstract String Author();
    public abstract String Bibliography();
    public abstract String Words();
}
