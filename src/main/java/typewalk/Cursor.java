package typewalk;

import com.google.common.collect.ImmutableList;
import lombok.val;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.stream.Stream.iterate;
import static lang.Lists.last;
import static lang.Streams.takeWhile;

public final class Cursor {
    private final Class<?> root;
    private final ImmutableList<Field> path; // sorted by distance from root

    private Cursor(Class<?> root, ImmutableList<Field> path) {
        this.root = root;
        this.path = path;
    }

    public Optional<Cursor> advance() {
        return path.isEmpty()
                ? Optional.empty()
                : Optional.of(new Cursor(root, nextPath()));
    }

    public static Cursor of(Class<?> type) {
        val branch = leftMostBranch(type);
        return new Cursor(type, branch);
    }

    private Optional<Field> findNextSibling() {
        val parent = thisProperty().getDeclaringClass();
        val properties = properties(parent);
        val index = properties.indexOf(thisProperty());
        return index < properties.size() - 1
                ? Optional.of(properties.get(index + 1))
                : Optional.empty();
    }

    private ImmutableList<Field> leftMostBranchThrough(Field sibling) {
        return ImmutableList.<Field>builder()
                .addAll(pathMinusLast())
                .add(sibling)
                .addAll(leftMostBranch(sibling.getType()))
                .build();
    }

    private ImmutableList<Field> nextPath() {
        return findNextSibling()
                .map(this::leftMostBranchThrough)
                .orElse(pathToParent());
    }

    private ImmutableList<Field> pathMinusLast() {
        return path.subList(0, path.size() - 1);
    }

    private ImmutableList<Field> pathToParent() {
        return pathMinusLast();
    }

    private Field thisProperty() {
        return last(path);
    }

    private static Optional<Field> findFirstProperty(Class<?> type) {
        return isLeaf(type)
                ? Optional.empty()
                : Optional.of(type.getFields()[0]);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static Optional<Field> findFirstProperty(Optional<Field> opt) {
        return opt.flatMap(field -> findFirstProperty(field.getType()));
    }

    private static boolean isLeaf(Class<?> c) {
        return c.isPrimitive();
    }

<<<<<<< HEAD
=======
    /**
     * @return - does not include the leaf ie. it can be empty if type is a leaf.
     */
>>>>>>> e82441ee062e9fe237b68a1e466bdbe98ba9c2dc
    private static ImmutableList<Field> leftMostBranch(Class<?> type) {
        val builder = ImmutableList.<Field>builder();
        takeWhile(Optional::isPresent, iterate(findFirstProperty(type), Cursor::findFirstProperty))
                .map(Optional::get)
                .forEach(builder::add);
        return builder.build();
    }

    private static List<Field> properties(Class<?> type) {
        return asList(type.getFields());
    }
}
