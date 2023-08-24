package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class UpdateLocationInput {
  private final @Nonnull String id;

  private final Input<String> type;

  private final Input<String> status;

  private final Input<List<AttributeInput>> attributes;

  private final Input<String> name;

  private final Input<String> supportPhoneNumber;

  private final Input<String> defaultCarrier;

  private final Input<UpdateStoreAddressInput> primaryAddress;

  private final Input<RetailerId> retailer;

  private final Input<UpdateOpeningScheduleInput> openingSchedule;

  private final Input<List<NetworkId>> networks;

  private final Input<List<UpdateStorageAreaWithLocationInput>> storageAreas;

  UpdateLocationInput(@Nonnull String id, Input<String> type, Input<String> status,
      Input<List<AttributeInput>> attributes, Input<String> name, Input<String> supportPhoneNumber,
      Input<String> defaultCarrier, Input<UpdateStoreAddressInput> primaryAddress,
      Input<RetailerId> retailer, Input<UpdateOpeningScheduleInput> openingSchedule,
      Input<List<NetworkId>> networks,
      Input<List<UpdateStorageAreaWithLocationInput>> storageAreas) {
    this.id = id;
    this.type = type;
    this.status = status;
    this.attributes = attributes;
    this.name = name;
    this.supportPhoneNumber = supportPhoneNumber;
    this.defaultCarrier = defaultCarrier;
    this.primaryAddress = primaryAddress;
    this.retailer = retailer;
    this.openingSchedule = openingSchedule;
    this.networks = networks;
    this.storageAreas = storageAreas;
  }

  /**
   *  ID of the object
   */
  public @Nonnull String id() {
    return this.id;
  }

  /**
   *  Max character limit: 25.
   */
  public @Nullable String type() {
    return this.type.value;
  }

  /**
   *  Max character limit: 15.
   */
  public @Nullable String status() {
    return this.status.value;
  }

  public @Nullable List<AttributeInput> attributes() {
    return this.attributes.value;
  }

  /**
   *  Max character limit: 100.
   */
  public @Nullable String name() {
    return this.name.value;
  }

  /**
   *  Max character limit: 20.
   */
  public @Nullable String supportPhoneNumber() {
    return this.supportPhoneNumber.value;
  }

  /**
   *  Max character limit: 50.
   */
  public @Nullable String defaultCarrier() {
    return this.defaultCarrier.value;
  }

  public @Nullable UpdateStoreAddressInput primaryAddress() {
    return this.primaryAddress.value;
  }

  public @Nullable RetailerId retailer() {
    return this.retailer.value;
  }

  public @Nullable UpdateOpeningScheduleInput openingSchedule() {
    return this.openingSchedule.value;
  }

  public @Nullable List<NetworkId> networks() {
    return this.networks.value;
  }

  public @Nullable List<UpdateStorageAreaWithLocationInput> storageAreas() {
    return this.storageAreas.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeCustom("id", com.fluentretail.graphql.type.CustomType.ID, id);
        if (type.defined) {
          writer.writeString("type", type.value);
        }
        if (status.defined) {
          writer.writeString("status", status.value);
        }
        if (attributes.defined) {
          writer.writeList("attributes", attributes.value != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (AttributeInput $item : attributes.value) {
                listItemWriter.writeObject($item.marshaller());
              }
            }
          } : null);
        }
        if (name.defined) {
          writer.writeString("name", name.value);
        }
        if (supportPhoneNumber.defined) {
          writer.writeString("supportPhoneNumber", supportPhoneNumber.value);
        }
        if (defaultCarrier.defined) {
          writer.writeString("defaultCarrier", defaultCarrier.value);
        }
        if (primaryAddress.defined) {
          writer.writeObject("primaryAddress", primaryAddress.value != null ? primaryAddress.value.marshaller() : null);
        }
        if (retailer.defined) {
          writer.writeObject("retailer", retailer.value != null ? retailer.value.marshaller() : null);
        }
        if (openingSchedule.defined) {
          writer.writeObject("openingSchedule", openingSchedule.value != null ? openingSchedule.value.marshaller() : null);
        }
        if (networks.defined) {
          writer.writeList("networks", networks.value != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (NetworkId $item : networks.value) {
                listItemWriter.writeObject($item.marshaller());
              }
            }
          } : null);
        }
        if (storageAreas.defined) {
          writer.writeList("storageAreas", storageAreas.value != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (UpdateStorageAreaWithLocationInput $item : storageAreas.value) {
                listItemWriter.writeObject($item.marshaller());
              }
            }
          } : null);
        }
      }
    };
  }

  public static final class Builder {
    private @Nonnull String id;

    private Input<String> type = Input.absent();

    private Input<String> status = Input.absent();

    private Input<List<AttributeInput>> attributes = Input.absent();

    private Input<String> name = Input.absent();

    private Input<String> supportPhoneNumber = Input.absent();

    private Input<String> defaultCarrier = Input.absent();

    private Input<UpdateStoreAddressInput> primaryAddress = Input.absent();

    private Input<RetailerId> retailer = Input.absent();

    private Input<UpdateOpeningScheduleInput> openingSchedule = Input.absent();

    private Input<List<NetworkId>> networks = Input.absent();

    private Input<List<UpdateStorageAreaWithLocationInput>> storageAreas = Input.absent();

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
     *  Max character limit: 25.
     */
    public Builder type(@Nullable String type) {
      this.type = Input.fromNullable(type);
      return this;
    }

    /**
     *  Max character limit: 15.
     */
    public Builder status(@Nullable String status) {
      this.status = Input.fromNullable(status);
      return this;
    }

    public Builder attributes(@Nullable List<AttributeInput> attributes) {
      this.attributes = Input.fromNullable(attributes);
      return this;
    }

    /**
     *  Max character limit: 100.
     */
    public Builder name(@Nullable String name) {
      this.name = Input.fromNullable(name);
      return this;
    }

    /**
     *  Max character limit: 20.
     */
    public Builder supportPhoneNumber(@Nullable String supportPhoneNumber) {
      this.supportPhoneNumber = Input.fromNullable(supportPhoneNumber);
      return this;
    }

    /**
     *  Max character limit: 50.
     */
    public Builder defaultCarrier(@Nullable String defaultCarrier) {
      this.defaultCarrier = Input.fromNullable(defaultCarrier);
      return this;
    }

    public Builder primaryAddress(@Nullable UpdateStoreAddressInput primaryAddress) {
      this.primaryAddress = Input.fromNullable(primaryAddress);
      return this;
    }

    public Builder retailer(@Nullable RetailerId retailer) {
      this.retailer = Input.fromNullable(retailer);
      return this;
    }

    public Builder openingSchedule(@Nullable UpdateOpeningScheduleInput openingSchedule) {
      this.openingSchedule = Input.fromNullable(openingSchedule);
      return this;
    }

    public Builder networks(@Nullable List<NetworkId> networks) {
      this.networks = Input.fromNullable(networks);
      return this;
    }

    public Builder storageAreas(@Nullable List<UpdateStorageAreaWithLocationInput> storageAreas) {
      this.storageAreas = Input.fromNullable(storageAreas);
      return this;
    }

    public UpdateLocationInput build() {
      Utils.checkNotNull(id, "id == null");
      return new UpdateLocationInput(id, type, status, attributes, name, supportPhoneNumber, defaultCarrier, primaryAddress, retailer, openingSchedule, networks, storageAreas);
    }
  }
}
