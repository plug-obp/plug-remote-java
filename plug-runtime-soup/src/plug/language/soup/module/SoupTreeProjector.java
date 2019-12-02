package plug.language.soup.module;

import plug.language.soup.model.Behavior;
import plug.runtime.core.TreeItem;
import plug.runtime.core.defaults.DefaultTreeProjector;

public class SoupTreeProjector extends DefaultTreeProjector<Object, Behavior<Object>, Void> {
    @Override
    public TreeItem projectConfiguration(Object configuration) {
        return Object2TreeItem.getTreeItem(configuration);
    }

    @Override
    public TreeItem projectFireable(Behavior<Object> fireable) {
        return new TreeItem(fireable.name);
    }

    @Override
    public TreeItem projectPayload(Void payload) {
        return TreeItem.empty;
    }
}
