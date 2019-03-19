//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.chenjin.testPCQueue.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.springframework.util.StringUtils;

public class Sort implements Iterable<Sort.Order>, Serializable {
    private static final long serialVersionUID = 734359895760444893L;
    public static final Sort.Direction DEFAULT_DIRECTION;
    private List<Sort.Order> orders;

    public Sort() {
    }

    public Sort(Sort.Order... orders) {
        this(Arrays.asList(orders));
    }

    public Sort(List<Sort.Order> orders) {
        if (null != orders && !orders.isEmpty()) {
            this.orders = orders;
        } else {
            throw new IllegalArgumentException("You have to provide at least one sort property to sort by!");
        }
    }

    public Sort(String... properties) {
        this(DEFAULT_DIRECTION, properties);
    }

    public Sort(Sort.Direction direction, String... properties) {
        this(direction, (List)(properties == null ? new ArrayList() : Arrays.asList(properties)));
    }

    public Sort(Sort.Direction direction, List<String> properties) {
        if (properties != null && !properties.isEmpty()) {
            this.orders = new ArrayList(properties.size());
            Iterator var3 = properties.iterator();

            while(var3.hasNext()) {
                String property = (String)var3.next();
                this.orders.add(new Sort.Order(direction, property));
            }

        } else {
            throw new IllegalArgumentException("You have to provide at least one property to sort by!");
        }
    }

    public Sort and(Sort sort) {
        if (sort == null) {
            return this;
        } else {
            ArrayList<Sort.Order> these = new ArrayList(this.orders);
            Iterator var3 = sort.iterator();

            while(var3.hasNext()) {
                Sort.Order order = (Sort.Order)var3.next();
                these.add(order);
            }

            return new Sort(these);
        }
    }

    public Sort.Order getOrderFor(String property) {
        Iterator var2 = this.iterator();

        Sort.Order order;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            order = (Sort.Order)var2.next();
        } while(!order.getProperty().equals(property));

        return order;
    }

    public Iterator<Sort.Order> iterator() {
        return this.orders.iterator();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Sort)) {
            return false;
        } else {
            Sort that = (Sort)obj;
            return this.orders.equals(that.orders);
        }
    }

    public int hashCode() {
        int result = 17;
        result = 31 * result + this.orders.hashCode();
        return result;
    }

    public String toString() {
        return StringUtils.collectionToCommaDelimitedString(this.orders);
    }

    static {
        DEFAULT_DIRECTION = Sort.Direction.ASC;
    }

    public static class Order implements Serializable {
        private static final long serialVersionUID = 1283736676629234075L;
        private Sort.Direction direction;
        private String property;

        public Order() {
        }

        public Order(Sort.Direction direction, String property) {
            if (!StringUtils.hasText(property)) {
                throw new IllegalArgumentException("Property must not null or empty!");
            } else {
                this.direction = direction == null ? Sort.DEFAULT_DIRECTION : direction;
                this.property = property;
            }
        }

        public Order(String property) {
            this(Sort.DEFAULT_DIRECTION, property);
        }

        /** @deprecated */
        @Deprecated
        public static List<Sort.Order> create(Sort.Direction direction, Iterable<String> properties) {
            List<Sort.Order> orders = new ArrayList();
            Iterator var3 = properties.iterator();

            while(var3.hasNext()) {
                String property = (String)var3.next();
                orders.add(new Sort.Order(direction, property));
            }

            return orders;
        }

        public Sort.Direction getDirection() {
            return this.direction;
        }

        public String getProperty() {
            return this.property;
        }

        public boolean isAscending() {
            return this.direction.equals(Sort.Direction.ASC);
        }

        public Sort.Order with(Sort.Direction order) {
            return new Sort.Order(order, this.property);
        }

        public Sort withProperties(String... properties) {
            return new Sort(this.direction, properties);
        }

        public int hashCode() {
            int result = 17;
            result = 31 * result + this.direction.hashCode();
            result = 31 * result + this.property.hashCode();
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (!(obj instanceof Sort.Order)) {
                return false;
            } else {
                Sort.Order that = (Sort.Order)obj;
                return this.direction.equals(that.direction) && this.property.equals(that.property);
            }
        }

        public String toString() {
            return String.format("%s: %s", this.property, this.direction);
        }
    }

    public static enum Direction {
        ASC,
        DESC;

        private Direction() {
        }

        public static Sort.Direction fromString(String value) {
            try {
                return valueOf(value.toUpperCase(Locale.US));
            } catch (Exception var2) {
                throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), var2);
            }
        }
    }
}
