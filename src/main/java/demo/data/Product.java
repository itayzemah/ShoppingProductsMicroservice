package demo.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Lob;

@Entity
@Table(name="Products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Product {
	private @NonNull @Id String id;
	private @NonNull String name;
	private double price;
	private @NonNull String image;
	private @NonNull @Lob String productDetails;
	private @NonNull @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) Category category;
}
