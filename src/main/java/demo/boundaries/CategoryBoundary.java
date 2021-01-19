package demo.boundaries;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CategoryBoundary {
	private @NonNull String name;
	private @NonNull String description;
	private String parentCategory;
}