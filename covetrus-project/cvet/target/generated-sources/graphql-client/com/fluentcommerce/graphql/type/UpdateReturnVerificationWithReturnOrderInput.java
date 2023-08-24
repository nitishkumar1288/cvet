package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class UpdateReturnVerificationWithReturnOrderInput {
  private final @Nonnull String ref;

  private final Input<String> verificationDetails;

  UpdateReturnVerificationWithReturnOrderInput(@Nonnull String ref,
      Input<String> verificationDetails) {
    this.ref = ref;
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
   * verification details
   */
  public @Nullable String verificationDetails() {
    return this.verificationDetails.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("ref", ref);
        if (verificationDetails.defined) {
          writer.writeString("verificationDetails", verificationDetails.value);
        }
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private Input<String> verificationDetails = Input.absent();

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
     * verification details
     */
    public Builder verificationDetails(@Nullable String verificationDetails) {
      this.verificationDetails = Input.fromNullable(verificationDetails);
      return this;
    }

    public UpdateReturnVerificationWithReturnOrderInput build() {
      Utils.checkNotNull(ref, "ref == null");
      return new UpdateReturnVerificationWithReturnOrderInput(ref, verificationDetails);
    }
  }
}
