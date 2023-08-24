package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import java.io.IOException;
import java.lang.Double;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class StreetAddressInput {
  private final Input<String> companyName;

  private final Input<String> name;

  private final Input<String> street;

  private final Input<String> city;

  private final Input<String> state;

  private final Input<String> postcode;

  private final Input<String> region;

  private final Input<String> country;

  private final Input<Double> latitude;

  private final Input<Double> longitude;

  private final Input<String> timeZone;

  StreetAddressInput(Input<String> companyName, Input<String> name, Input<String> street,
      Input<String> city, Input<String> state, Input<String> postcode, Input<String> region,
      Input<String> country, Input<Double> latitude, Input<Double> longitude,
      Input<String> timeZone) {
    this.companyName = companyName;
    this.name = name;
    this.street = street;
    this.city = city;
    this.state = state;
    this.postcode = postcode;
    this.region = region;
    this.country = country;
    this.latitude = latitude;
    this.longitude = longitude;
    this.timeZone = timeZone;
  }

  /**
   *  Company Name. <br/>
   *  Max character limit: 45.
   */
  public @Nullable String companyName() {
    return this.companyName.value;
  }

  /**
   *  Name
   */
  public @Nullable String name() {
    return this.name.value;
  }

  /**
   *  Street. <br/>
   *  Max character limit: 100.
   */
  public @Nullable String street() {
    return this.street.value;
  }

  /**
   *  City. <br/>
   *  Max character limit: 45.
   */
  public @Nullable String city() {
    return this.city.value;
  }

  /**
   *  State. <br/>
   *  Max character limit: 200.
   */
  public @Nullable String state() {
    return this.state.value;
  }

  /**
   *  Postcode. <br/>
   *  Max character limit: 100.
   */
  public @Nullable String postcode() {
    return this.postcode.value;
  }

  /**
   *  Region. <br/>
   *  Max character limit: 250.
   */
  public @Nullable String region() {
    return this.region.value;
  }

  /**
   *  Country. <br/>
   *  Max character limit: 100.
   */
  public @Nullable String country() {
    return this.country.value;
  }

  /**
   *  Latitude
   */
  public @Nullable Double latitude() {
    return this.latitude.value;
  }

  /**
   *  Longitude
   */
  public @Nullable Double longitude() {
    return this.longitude.value;
  }

  /**
   *  Timezone. <br/>
   *  Max character limit: 32.
   */
  public @Nullable String timeZone() {
    return this.timeZone.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        if (companyName.defined) {
          writer.writeString("companyName", companyName.value);
        }
        if (name.defined) {
          writer.writeString("name", name.value);
        }
        if (street.defined) {
          writer.writeString("street", street.value);
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
      }
    };
  }

  public static final class Builder {
    private Input<String> companyName = Input.absent();

    private Input<String> name = Input.absent();

    private Input<String> street = Input.absent();

    private Input<String> city = Input.absent();

    private Input<String> state = Input.absent();

    private Input<String> postcode = Input.absent();

    private Input<String> region = Input.absent();

    private Input<String> country = Input.absent();

    private Input<Double> latitude = Input.absent();

    private Input<Double> longitude = Input.absent();

    private Input<String> timeZone = Input.absent();

    Builder() {
    }

    /**
     *  Company Name. <br/>
     *  Max character limit: 45.
     */
    public Builder companyName(@Nullable String companyName) {
      this.companyName = Input.fromNullable(companyName);
      return this;
    }

    /**
     *  Name
     */
    public Builder name(@Nullable String name) {
      this.name = Input.fromNullable(name);
      return this;
    }

    /**
     *  Street. <br/>
     *  Max character limit: 100.
     */
    public Builder street(@Nullable String street) {
      this.street = Input.fromNullable(street);
      return this;
    }

    /**
     *  City. <br/>
     *  Max character limit: 45.
     */
    public Builder city(@Nullable String city) {
      this.city = Input.fromNullable(city);
      return this;
    }

    /**
     *  State. <br/>
     *  Max character limit: 200.
     */
    public Builder state(@Nullable String state) {
      this.state = Input.fromNullable(state);
      return this;
    }

    /**
     *  Postcode. <br/>
     *  Max character limit: 100.
     */
    public Builder postcode(@Nullable String postcode) {
      this.postcode = Input.fromNullable(postcode);
      return this;
    }

    /**
     *  Region. <br/>
     *  Max character limit: 250.
     */
    public Builder region(@Nullable String region) {
      this.region = Input.fromNullable(region);
      return this;
    }

    /**
     *  Country. <br/>
     *  Max character limit: 100.
     */
    public Builder country(@Nullable String country) {
      this.country = Input.fromNullable(country);
      return this;
    }

    /**
     *  Latitude
     */
    public Builder latitude(@Nullable Double latitude) {
      this.latitude = Input.fromNullable(latitude);
      return this;
    }

    /**
     *  Longitude
     */
    public Builder longitude(@Nullable Double longitude) {
      this.longitude = Input.fromNullable(longitude);
      return this;
    }

    /**
     *  Timezone. <br/>
     *  Max character limit: 32.
     */
    public Builder timeZone(@Nullable String timeZone) {
      this.timeZone = Input.fromNullable(timeZone);
      return this;
    }

    public StreetAddressInput build() {
      return new StreetAddressInput(companyName, name, street, city, state, postcode, region, country, latitude, longitude, timeZone);
    }
  }
}
