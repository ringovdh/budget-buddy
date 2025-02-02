import { Comment } from "../admin/comment/comment"

export interface Page<T> {
  "content": T[],
  "pageable":{
    "sort":{
      "unsorted":boolean,
      "sorted":boolean,
      "empty":boolean
    },
    "pageNumber":number,
    "pageSize":number,
    "offset":number,
    "paged":boolean,
    "unpaged":boolean
  },
  "totalPages":number,
  "totalElements":number,
  "last":boolean,
  "numberOfElements":number,
  "first":boolean,
  "size":number,
  "sort":{
    "unsorted":boolean,
    "sorted":boolean,
    "empty":boolean
  },
  "number":number,
  "empty":boolean
}
