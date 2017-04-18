
package chatty.util.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * One or several consecutive Item objects. This is the top-level element of a
 * Custom Command, but can also appear in function parameters.
 * 
 * @author tduva
 */
class Items implements Item {

    private final List<Item> collection;
    private StringBuilder builder = new StringBuilder();

    public Items() {
        this.collection = new ArrayList<>();
    }

    public Items(List<Item> collection) {
        this.collection = collection;
    }

    public void add(Item item) {
        flush();
        this.collection.add(item);
    }

    public void add(String literal) {
        builder.append(literal);
    }

    public Item getItem(int index) {
        return collection.get(index);
    }

    public void flush() {
        if (builder.length() > 0) {
            collection.add(new Literal(builder.toString()));
            builder = new StringBuilder();
        }
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public String replace(Parameters parameters, boolean isRequired) {
        String result = replace(parameters);
        return result != null || isRequired ? result : "";
    }

    @Override
    public String replace(Parameters parameters) {
        StringBuilder b = new StringBuilder();
        for (Item item : collection) {
            String replaced = item.replace(parameters);
            if (replaced == null) {
                return null;
            }
            b.append(replaced);
        }
        return b.toString();
    }

    @Override
    public String toString() {
        return collection.toString();
    }

    @Override
    public Set<String> getIdentifiersWithPrefix(String prefix) {
        return Item.getIdentifiersWithPrefix(prefix, collection.toArray());
    }

}
