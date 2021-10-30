package domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class OrderItem {
    private String id;
    private final String name;
    private final int quantity;
    private final int price;
}
