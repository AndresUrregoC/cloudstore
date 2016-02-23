package com.grability.cloudstore.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AndresDev on 21/02/16.
 */
public class Apps {

    @Expose
    private Feed feed;

    public Feed getFeed() {
        return feed;
    }

    public class Feed{

        @Expose
        private List<Entry> entry;

        public List<Entry> getEntry() {
            return entry;
        }

        public class Entry{

            @SerializedName("im:name")
            private Name name;

            @SerializedName("im:image")
            private List<Image> image;

            @Expose
            private Summary summary;

            @Expose
            private Rights rights;

            @Expose
            private Id id;

            @Expose
            private Category category;

            @SerializedName("im:artist")
            private Artist artist;

            public class Name{
                @Expose
                private String label;

                public String getLabel() {
                    return label;
                }
            }

            public class Image{
                @Expose
                private String label;

                public String getLabel() {
                    return label;
                }
            }

            public class Summary{

                @Expose
                private String label;

                public String getLabel() {
                    return label;
                }
            }

            public class Rights{

                @Expose
                private String label;

                public String getLabel() {
                    return label;
                }
            }

            public class Id{

                @Expose
                private Attributes attributes;

                public Attributes getAttributes() {
                    return attributes;
                }

                public class Attributes{

                    @SerializedName("im:id")
                    private String id;

                    public String getId() {
                        return id;
                    }
                }
            }

            public class Artist{

                @Expose
                private String label;

                public String getLabel() {
                    return label;
                }
            }

            public class Category{

                @Expose
                private Attributes attributes;

                public Attributes getAttributes() {
                    return attributes;
                }

                public class Attributes{

                    @SerializedName("id:id")
                    private String id;

                    @Expose
                    private String label;

                    public String getId() {
                        return id;
                    }

                    public String getLabel() {
                        return label;
                    }
                }
            }

            public Name getName() {
                return name;
            }

            public List<Image> getImage() {
                return image;
            }

            public Summary getSummary() {
                return summary;
            }

            public Rights getRights() {
                return rights;
            }

            public Id getId() {
                return id;
            }

            public Category getCategory() {
                return category;
            }

            public Artist getArtist() {
                return artist;
            }
        }
    }
}
