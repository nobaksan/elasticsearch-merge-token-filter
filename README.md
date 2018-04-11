# Merge Token Filter for Elasticsearch

The Merge Token Filter plugin provides [token filter](http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/analysis-tokenfilters.html).

## Installation

### Plugin

Beginning with elasticsearch 6 installation is following:

```bash
# specify elasticsearch version
#
export VERSION=6.1.1
./bin/elasticsearch-plugin install https://github.com/nobaksan/elasticsearch-merge-token-filter/releases/download/v$VERSION/elasticsearch-merge-token-filter-$VERSION-plugin.zip
```

For older elasticsearch version see installation instructions in [**releases section**](hthttps://github.com/nobaksan/elasticsearch-merge-token-filter/releases).

## Usage

This plugin provides [token filter](http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/analysis-tokenfilters.html) of type `merge_token_filter`.

## Example

```bash
# Delete test index
#
curl -H "Content-Type: application/json" -X DELETE 'http://localhost:9200/test-filter'

# Create index with lemmagen filter
#
curl -H "Content-Type: application/json" -X PUT 'http://localhost:9200/test-filter' -d '{
            "settings": {
                "index": {
                  "analysis": {
                    "filter": {
                      "merge_token_filter": {
                        "type": "merge_token_filter"
                      }
                    },
                    "analyzer": {
                      "mrege_analyzer": {
                        "type": "custom",
                        "tokenizer": "standard",
                        "filter": [
                          "merge_token_filter"
                        ]
                      }
                    }
                  }
                }
              },
              "mappings": {
                "message": {
                  "properties": {
                    "text": {
                      "type": "text",
                      "analyzer": "mrege_analyzer"
                    }
                  }
                }
              }
          }'

# Try it using _analyze api
#
curl -H "Content-Type: application/json" -X GET 'http://localhost:9200/test-filter/_analyze?pretty' -d '{
  "text": "I am late.",
  "analyzer": "mrege_analyzer"
}'

# RESPONSE:
#
# {
#    "tokens": [
#      {
#        "token": "I_am_late",
#        "start_offset": 0,
#        "end_offset": 0,
#        "type": "word",
#        "position": 0
#      }
#    ]
#  }

# Index document
#
curl -H "Content-Type: application/json" -XPUT 'http://localhost:9200/curl -H "Content-Type: application/json" -XPUT 'http://localhost:9200/test-filter/message/1?refresh=wait_for' -d '{
    "user"         : "kimchy",
    "published_at" : "2018-04-10T18:08:12",
    "text"         : "I am late"
}'


```

## Development

To copy dependencies located in `lib` directory to you local maven repository (`~/.m2`) run:

```bash
mvn initialize
```

and to create plugin package run following:

```bash
mvn package
```

After that build should be located in `./target/releases`.

License
=======
All source codes are licensed under Apache License, Version 2.0.
```
                                 Apache License
                           Version 2.0, January 2004
                           
   TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION

   1. Definitions.

      "License" shall mean the terms and conditions for use, reproduction,
      and distribution as defined by Sections 1 through 9 of this document.

      "Licensor" shall mean the copyright owner or entity authorized by
      the copyright owner that is granting the License.

      "Legal Entity" shall mean the union of the acting entity and all
      other entities that control, are controlled by, or are under common
      control with that entity. For the purposes of this definition,
      "control" means (i) the power, direct or indirect, to cause the
      direction or management of such entity, whether by contract or
      otherwise, or (ii) ownership of fifty percent (50%) or more of the
      outstanding shares, or (iii) beneficial ownership of such entity.
  ```