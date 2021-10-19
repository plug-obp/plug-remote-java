package plug.runtime.core;

import java.util.ArrayList;
import java.util.List;

public class TreeItem {
    public String type;
    public String name;
    public String icon;
    public List<TreeItem> children;

    public static final TreeItem empty = new TreeItem("empty");

    public TreeItem(String type, String name, String icon, List<TreeItem> children) {
        this.type = type == null ? "" : type;
        this.name = name == null ? "" : name;
        this.icon = icon == null ? "" : icon;
        this.children = children == null ? new ArrayList<>() : children;
    }

    public TreeItem(String name) {
        this(null, name, null, null);
    }

    @Override
    public String toString() {
        String str = "" + name + " [";
        for (TreeItem child : children) {
            str += child.toString();
        }
        str += "]";
        return str;
    }
}
