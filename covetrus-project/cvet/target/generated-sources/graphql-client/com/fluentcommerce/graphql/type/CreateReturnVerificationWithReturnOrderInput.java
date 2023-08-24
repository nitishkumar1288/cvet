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
public final class CreateReturnVerificationWithReturnOrderInput {
  private final @Nonnull String ref;

  private final @Nonnull String type;

  private final @Nonnull String verificationDetails;

  CreateReturnVerificationWithReturnOrderInput(@Nonnull String ref, @Nonnull String type,
      @Nonnull String verificationDetails) {
    this.ref = ref;
    this.type = type;
    this.verificationDetails = verificationDetails;
  }

  /**
   *  External reference. Must be unique. <br/>
   *  Max character limit: 100.
   */
  public @Nonnull String ref() {
    return this.ref;
  }

  /**
   * Type
   */
  public @Nonnull String type() {
    return this.type;
  }

  /**
   * verification details
   */
  public @Nonnull String verificationDetails() {
    return this.verificationDetails;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("ref", ref);
        writer.writeString("type", type);
        writer.writeString("verificationDetails", verificationDetails);
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private @Nonnull String type;

    private @Nonnull String verificationDetails;

    Builder() {
    }

    /**
     *  External reference. Must be unique. <br/>
     *  Max character limit: 100.
     */
    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    /**
     * Type
     */
    public Builder type(@Nonnull String type) {
      this.type = type;
      return this;
    }

    /**
     * verification details
     */
    public Builder verificationDetails(@Nonnull String verificationDetails) {
      this.verificationDetails = verificationDetails;
      return this;
    }

    public CreateReturnVerificationWithReturnOrderInput build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(type, "type == null");
      Utils.checkNotNull(verificationDetails, "verificationDetails == null");
      return new CreateReturnVerificationWithReturnOrderInput(ref, type, verificationDetails);
    }
  }
}
