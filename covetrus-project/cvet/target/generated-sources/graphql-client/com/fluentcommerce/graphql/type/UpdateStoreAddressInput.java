package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Double;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class UpdateStoreAddressInput {
  private final @Nonnull String id;

  private final Input<String> directions;

  private final Input<String> companyName;

  private final Input<String> name;

  private final Input<String> street;

  private final Input<String> street2;

  private final Input<String> city;

  private final Input<String> state;

  private final Input<String> postcode;

  private final Input<String> region;

  private final Input<String> country;

  private final Input<Double> latitude;

  private final Input<Double> longitude;

  private final Input<String> timeZone;

  private final Input<String> email;

  UpdateStoreAddressInput(@Nonnull String id, Input<String> directions, Input<String> companyName,
      Input<String> name, Input<String> street, Input<String> street2, Input<String> city,
      Input<String> state, Input<String> postcode, Input<String> region, Input<String> country,
      Input<Double> latitude, Input<Double> longitude, Input<String> timeZone,
      Input<String> email) {
    this.id = id;
    this.directions = directions;
    this.companyName = companyName;
    this.name = name;
    this.street = street;
    this.street2 = street2;
    this.city = city;
    this.state = state;
    this.postcode = postcode;
    this.region = region;
    this.country = country;
    this.latitude = latitude;
    this.longitude = longitude;
    this.timeZone = timeZone;
    this.email = email;
  }

  /**
   *  ID of the object
   */
  public @Nonnull String id() {
    return this.id;
  }

  /**
   *  Max character limit: 4096.
   */
  public @Nullable String directions() {
    return this.directions.value;
  }

  /**
   *  Max character limit: 255.
   */
  public @Nullable String companyName() {
    return this.companyName.value;
  }

  public @Nullable String name() {
    return this.name.value;
  }

  /**
   *  Max character limit: 255.
   */
  public @Nullable String street() {
    return this.street.value;
  }

  /**
   *  Max character limit: 255.
   */
  public @Nullable String street2() {
    return this.street2.value;
  }

  /**
   *  Max character limit: 255.
   */
  public @Nullable String city() {
    return this.city.value;
  }

  /**
   *  Max character limit: 200.
   */
  public @Nullable String state() {
    return this.state.value;
  }

  /**
   *  Max character limit: 100.
   */
  public @Nullable String postcode() {
    return this.postcode.value;
  }

  /**
   *  Max character limit: 250.
   */
  public @Nullable String region() {
    return this.region.value;
  }

  /**
   *  Max character limit: 100.
   */
  public @Nullable String country() {
    return this.country.value;
  }

  public @Nullable Double latitude() {
    return this.latitude.value;
  }

  public @Nullable Double longitude() {
    return this.longitude.value;
  }

  /**
   *  Max character limit: 32.
   */
  public @Nullable String timeZone() {
    return this.timeZone.value;
  }

  /**
   *  Max character limit: 255.
   */
  public @Nullable String email() {
    return this.email.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeCustom("id", com.fluentretail.graphql.type.CustomType.ID, id);
        if (directions.defined) {
          writer.writeString("directions", directions.value);
        }
        if (companyName.defined) {
          writer.writeString("companyName", companyName.value);
        }
        if (name.defined) {
          writer.writeString("name", name.value);
        }
        if (street.defined) {
          writer.writeString("street", street.value);
        }
        if (street2.defined) {
          writer.writeString("street2", street2.value);
        }
        if (city.defined) {
          writer.writeString("city", city.value);
        }
        if (state.defined) {
          writer.writeString("state", state.value);
        }
        if (postcode.defined) {
          writer.writeString("postcode", postcode.value);
        }
        if (region.defined) {
          writer.writeString("region", region.value);
        }
        if (country.defined) {
          writer.writeString("country", country.value);
        }
        if (latitude.defined) {
          writer.writeDouble("latitude", latitude.value);
        }
        if (longitude.defined) {
          writer.writeDouble("longitude", longitude.value);
        }
        if (timeZone.defined) {
          writer.writeString("timeZone", timeZone.value);
        }
        if (email.defined) {
          writer.writeString("email", email.value);
        }
      }
    };
  }

  public static final class Builder {
    private @Nonnull String id;

    private Input<String> directions = Input.absent();

    private Input<String> companyName = Input.absent();

    private Input<String> name = Input.absent();

    private Input<String> street = Input.absent();

    private Input<String> street2 = Input.absent();

    private Input<String> city = Input.absent();

    private Input<String> state = Input.absent();

    private Input<String> postcode = Input.absent();

    private Input<String> region = Input.absent();

    private Input<String> country = Input.absent();

    private Input<Double> latitude = Input.absent();

    private Input<Double> longitude = Input.absent();

    private Input<String> timeZone = Input.absent();

    private Input<String> email = Input.absent();

    Builder() {
    }

    /**
     *  ID of the object
     */
    public Builder id(@Nonnull String id) {
      this.id = id;
      return this;
    }

    /**
     *  Max character limit: 4096.
     */
    public Builder directions(@Nullable String directions) {
      this.directions = Input.fromNullable(directions);
      return this;
    }

    /**
     *  Max character limit: 255.
     */
    public Builder companyName(@Nullable String companyName) {
      this.companyName = Input.fromNullable(companyName);
      return this;
    }

    public Builder name(@Nullable String name) {
      this.name = Input.fromNullable(name);
      return this;
    }

    /**
     *  Max character limit: 255.
     */
    public Builder street(@Nullable String street) {
      this.street = Input.fromNullable(street);
      return this;
    }

    /**
     *  Max character limit: 255.
     */
    public Builder street2(@Nullable String street2) {
      this.street2 = Input.fromNullable(street2);
      return this;
    }

    /**
     *  Max character limit: 255.
     */
    public Builder city(@Nullable String city) {
      this.city = Input.fromNullable(city);
      return this;
    }

    /**
     *  Max character limit: 200.
     */
    public Builder state(@Nullable String state) {
      this.state = Input.fromNullable(state);
      return this;
    }

    /**
     *  Max character limit: 100.
     */
    public Builder postcode(@Nullable String postcode) {
      this.postcode = Input.fromNullable(postcode);
      return this;
    }

    /**
     *  Max character limit: 250.
     */
    public Builder region(@Nullable String region) {
      this.region = Input.fromNullable(region);
      return this;
    }

    /**
     *  Max character limit: 100.
     */
    public Builder country(@Nullable String country) {
      this.country = Input.fromNullable(country);
      return this;
    }

    public Builder latitude(@Nullable Double latitude) {
      this.latitude = Input.fromNullable(latitude);
      return this;
    }

    public Builder longitude(@Nullable Double longitude) {
      this.longitude = Input.fromNullable(longitude);
      return this;
    }

    /**
     *  Max character limit: 32.
     */
    public Builder timeZone(@Nullable String timeZone) {
      this.timeZone = Input.fromNullable(timeZone);
      return this;
    }

    /**
     *  Max character limit: 255.
     */
    public Builder email(@Nullable String email) {
      this.email = Input.fromNullable(email);
      return this;
    }

    public UpdateStoreAddressInput build() {
      Utils.checkNotNull(id, "id == null");
      return new UpdateStoreAddressInput(id, directions, companyName, name, street, street2, city, state, postcode, region, country, latitude, longitude, timeZone, email);
    }
  }
}
