# Thomas User Guide

Thomas manages tasks and a separate collection of saved places. Task commands and place commands have separate numbering: a place never appears in `list`, `find`, `mark`, `unmark`, or task `delete`.

## Saved places

### Add a place: `place`

`place NAME /type TYPE /at ADDRESS /rating 1-5 /price AMOUNT [/visited YYYY-MM-DD] [/note NOTE]`

Example: `place Ichiban Sushi /type Japanese restaurant /at 10 Anson Road /rating 4 /price 28.50 /note Great lunch`

Name, type, address, rating, and price are required. Rating is a whole number from 1 to 5. Price is a non-negative decimal amount with at most two decimal places and is shown with two decimal places. Date visited uses `YYYY-MM-DD`. Text fields cannot contain `|` or line breaks because saved-place data uses a readable pipe-delimited file.

### List and find places

`listplace` displays every saved place. `findplace KEYWORD` finds places whose names contain `KEYWORD`, ignoring letter case. The displayed number is the place's current number in the complete place list, so it can be used with `editplace` and `deleteplace`.

### Edit a place: `editplace`

```
editplace INDEX /name NAME
editplace INDEX /type TYPE
editplace INDEX /at ADDRESS
editplace INDEX /rating 1-5
editplace INDEX /price AMOUNT
editplace INDEX /visited YYYY-MM-DD
editplace INDEX /visited none
editplace INDEX /note NOTE
editplace INDEX /note none
```

Each command changes exactly one field. `none` clears an optional visit date or note. Place indices are one-based; duplicates are allowed, so name is never used as the record identifier.

### Delete a place: `deleteplace`

Run `deleteplace INDEX`, then `confirmdeleteplace INDEX`. The first command displays the selected place but does not delete it. Run the matching confirmation command next to delete it. Any other command, including an invalid command, cancels the pending deletion.

## Storage and compatibility

Places are saved independently in `data/places.txt`. Existing tasks remain in `data/thomas.txt` and continue to load exactly as before. A missing place file is created automatically. Malformed place records are skipped without preventing other valid records from loading.
