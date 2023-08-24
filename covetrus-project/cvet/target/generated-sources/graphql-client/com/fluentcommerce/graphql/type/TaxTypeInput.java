package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("Apollo GraphQL")
public final class TaxTypeInput {
  private final @Nonnull String country;

  private final @Nonnull String group;

  private final @Nonnull String tariff;

  TaxTypeInput(@Nonnull String country, @Nonnull String group, @Nonnull String tariff) {
    this.country = country;
    this.group = group;
    this.tariff = tariff;
  }

  /**
   *  Max character limit: 100.
   */
  public @Nonnull String country() {
    return this.country;
  }

  /**
   *  Max character limit: 100.
   */
  public @Nonnull String group() {
    return this.group;
  }

  /**
   *  Max character limit: 100.
   */
  public @Nonnull String tariff() {
    return this.tariff;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("country", country);
        writer.writeString("group", group);
        writer.writeString("tariff", tariff);
      }
    };
  }

  public static final class Builder {
    private @Nonnull String country;

    private @Nonnull String group;

    private @Nonnull String tariff;

    Builder() {
    }

    /**
     *  Max character limit: 100.
     */
    public Builder country(@Nonnull String country) {
      this.country = country;
      return this;
    }

    /**
     *  Max character limit: 100.
     */
    public Builder group(@Nonnull String group) {
      this.group = group;
      return this;
    }

    /**
     *  Max character limit: 100.
     */
    public Builder tariff(@Nonnull String tariff) {
      this.tariff = tariff;
      return this;
    }

    public TaxTypeInput build() {
      Utils.checkNotNull(country, "country == null");
      Utils.checkNotNull(group, "group == null");
      Utils.checkNotNull(tariff, "tariff == null");
      return new TaxTypeInput(country, group, tariff);
    }
  }
}
