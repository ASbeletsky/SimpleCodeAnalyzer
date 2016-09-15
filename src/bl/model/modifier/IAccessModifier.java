package bl.model.modifier;

/**
 * Created by Peter on 15.09.2016.
 */
public interface IAccessModifier {
    Visibility getAccessModifier();

    enum Visibility {
        PUBLIC,
        PROTECTED,
        PACKAGE_LOCAL,
        PRIVATE
    }
}
