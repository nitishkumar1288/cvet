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
public final class CurrencyKey {
  private final @Nonnull String alphabeticCode;

  CurrencyKey(@Nonnull String alphabeticCode) {
    this.alphabeticCode = alphabeticCode;
  }

  /**
   *  Max character limit: 20.
   */
  public @Nonnull String alphabeticCode() {
    return this.alphabeticCode;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("alphabeticCode", alphabeticCode);
      }
    };
  }

  public static final class Builder {
    private @Nonnull String alphabeticCode;

    Builder() {
    }

    /**
     *  Max character limit: 20.
     */
    public Builder alphabeticCode(@Nonnull String alphabeticCode) {
      this.alphabeticCode = alphabeticCode;
      return this;
    }

    public CurrencyKey build() {
      Utils.checkNotNull(alphabeticCode, "alphabeticCode == null");
      return new CurrencyKey(alphabeticCode);
    }
  }
}
