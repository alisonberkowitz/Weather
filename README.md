# MobileProto Lab 2

For this lab, we want to integrate what we've learned about a simple TODO list with data storage.
If you remember our breakdown of MVC in class before, our now looks like:

*  Model — We will store our TODO list items on the File System.
*  View — Layouts for creating new TODO items or for viewing individual ones.
*  Controller — Controls adding, removing, and displaying TODO items.

## Setup

Fork this repository on Github. Then clone it to a local directory. Go to "Import Project", choose
this directory, and click OK on the prompts that follow.

## Displaying a list of TODO items

Our first goal is to display a list of TODO items. Much like the last lab, we'll be starting from
scratch with MainActivity to do so.

First, create a new layout file named `note_list_item.xml`. Make sure that the root element is a
RelativeLayout. In the visual layout editor, add an ImageButton all the way at the far right of this
layout. We want the ImageButton's ID to be set to "deleteButton", and the background to be set to
"@drawable/delete" to display our X image.

Next, place a TextView to the left, starting at the furthest side and stretching to the ImageButton
itself.

Now, from Java, create a new class that extends NoteListAdapter. (Reminder: Alt+Enter imports
unimported classes labeled in red.) This class should

```xml
public class NoteListAdapter extends ArrayAdapter {

    private List<String> data;
    private Activity activity;

    public NoteListAdapter(Activity a, int viewResourceId, List<String> data){
        super(a, viewResourceId, data);
        this.data = data;
        this.activity = a;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v==null){
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.note_list_item, null);
        }

        ImageButton del = (ImageButton) v.findViewById(R.id.deleteButton);
        final TextView name = (TextView) v.findViewById(R.id.titleTextView);
        name.setText(data.get(position));

        // ...

        return v;
    }
}
```

In our main activity layout, add TextViews, EditText, and to approximate what was seen in the
video. Note that these

Lastly, in our main activity, make this the last code in onCreate:

```java
        final TextView title = (TextView) findViewById(R.id.titleField);
        final TextView note = (TextView) findViewById(R.id.noteField);


        List<String> files = new ArrayList<String>(Arrays.asList(fileList()));
        final NoteListAdapter aa = new NoteListAdapter(this, android.R.layout.simple_list_item_1, files);
        final ListView notes = (ListView) findViewById(R.id.noteList);
        notes.setAdapter(aa);
```

## Adding files to the Filesystem

To work with the file system, you'll be using the java.io.* classes. All of the operations you can
perform on a file system either from a command line or from a directory browser are possible to do
from inside your Android application. We'll see later why the Filesystem doesn't make sense for all
types of applications.

In your onCreate code in MainActivity, create a click listener for the Save button. (saveButton.setOnClickListener ...)

```java
                String fileName = title.getText().toString();
                String noteText = note.getText().toString();
                if (fileName != null && noteText != null){
                    try{
                        // ...
                    }catch (IOException e){
                        Log.e("IOException", e.getMessage());
                    }
                }
```

`IOException is the type of error thrown by interacting with the Filesystem. In the commented out bit,
start by adding this code.

```java
                        FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                        fos.write(noteText.getBytes());
                        fos.close();
```

The first bit is an Android function that opens up a file and returns a `FileOutputStream`. We then
write to the file using content from our TextView, then close the content.

Next, make the ListAdapter update with a new entry when we have noteText filled.

## Removing Files from the Filesystem (and the TODO list)

Next, in NoteListAdapter, we'll want to do a bit of the opposite—remove an entry from the list.
In your NoteListAdapter, add an onclick listener for the delete button for an entry. It should
be in the commented `// ...` portion of the adapter and your code uses `activity.deleteFile`.
Remember to remove the entry from `List<String> data` and run notifyDataSetChanged.

## TODO list in Detail

What's inside a TODO list item? If you've inspected the code we've written before, you note that we
write the contents of a TODO list item into the filesystem, but then only go ahead to read the code.

Instead, in your MainActivity, add a `notes.setOnItemClickListener`. The second argument to this function
is a `view`, and with that object you can get the name of the file you clicked.

We want you to create a new activity called `NoteDetailActivity` to be launched from the
ItemClickListener. Before we've shown you intents, which we'll use again to switch activities.
 You can also attach information to an Intent in order to direct the activity what it should display:

```java
                Intent in = new Intent(getApplicationContext(), NoteDetailActivity.class);
                in.putExtra("file", fileName);
                startActivity(in);
```

In your `NoteDetailActivity`, unpack this with the following lines in `onCreate`:

```java
        Intent intent = getIntent();
        String fileName = intent.getStringExtra("file");
```

Create a new layout `activity_note_detail.xml`. This should have two text fields, the top one
being the title (filename) of the file and the bottom being the contents of the file. Check the
video for reference. In `NoteDetailActivity`, make sure setContentView references this layout.

Next, use `findViewById` to reference the title and contents TextView. To load
the contents of a file, use the following code:

```
        StringBuilder fileText = new StringBuilder();
        try{
            FileInputStream fis = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null){
                fileText.append(line);
                fileText.append('\n');
            }

        }catch (IOException e){
            Log.e("IOException", e.getMessage());
        }
```

At the end, `fileText.toString()` is the source of your file. Load the file contents and the file
name into your textviews.


## Due Date

Thursday September 19