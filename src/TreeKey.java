public interface TreeKey {
    boolean match(TreeKey other);
    boolean isLessThan(TreeKey other);
}
