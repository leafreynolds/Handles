/*
 * File updated ~ 18 - 9 - 2023 ~ Leaf
 */

package leaf.handles.forge.datagen.patchouli;

import leaf.handles.forge.datagen.patchouli.data.BookStuff;
import leaf.handles.peripherals.HandlesFunction;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class PatchouliFunctionsCategory
{
	public static void collect(List<BookStuff.Category> categories, List<BookStuff.Entry> entries)
	{
		BookStuff.Category category = new BookStuff.Category(
				"Functions",
				"",
				"handles:fez");
		categories.add(category);
		category.sortnum = 1;

		// Start a page list
		List<BookStuff.Page> pages = new ArrayList<>();
		// Allomancy Basics Entry
		BookStuff.Entry basics = new BookStuff.Entry("basics", category, "minecraft:writable_book");
		basics.priority = true;

		BookStuff.Page firstPage = new BookStuff.TextPage();
		firstPage.setTitle("Handles (For Dummies)");
		firstPage.setText(
				"Handles works via putting a computer into your tardis and then attaching a tardis peripheral next to it. You're then able to write a program. $(br)" +
						"You can use the following to gain access to handles functions:$(br)" +
						"tardis = peripheral.find('tardis') or error('No tardis interface', 0)");
		pages.add(firstPage);

		pages.add(new BookStuff.CraftingPage("Tardis Peripheral", "handles:fez", "handles:antenna"));
		basics.pages = pages.toArray(BookStuff.Page[]::new);
		pages.clear();
		entries.add(basics);


		var methods = getAnnotatedMethods();

		for (int i = 0; i < methods.size(); i++)
		{
			var method = methods.get(i);

			String methodName = method.getLeft();
			HandlesFunction handlesFunction = method.getRight();

			BookStuff.Entry entry = new BookStuff.Entry(
					methodName,
					category
			);
			entry.sortnum = i + 1;


			BookStuff.Page page = new BookStuff.TextPage();
			page.setTitle(methodName);
			page.setText(
					"Description:$(br)" +
							handlesFunction.description() + "$(br)" +
							"$(br2) returns:$(br)" + handlesFunction.returns()
			);
			pages.add(page);

			BookStuff.Page example = new BookStuff.TextPage();
			example.setTitle("Example");
			example.setText(handlesFunction.example());
			pages.add(page);

			entry.pages = pages.toArray(BookStuff.Page[]::new);
			pages.clear();
			entries.add(entry);
		}

	}

	private static List<Pair<String, HandlesFunction>> getAnnotatedMethods()
	{
		Class<?> clazz;
		try
		{
			clazz = ClassLoader.getSystemClassLoader().loadClass("leaf.handles.peripherals.RefinedPeripheral");
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}

		Method[] methods = clazz.getMethods();

		List<Pair<String, HandlesFunction>> annotatedMethods = new ArrayList<>();

		//null?
		for (Method method : methods)
		{
			HandlesFunction annotation = method.getAnnotation(HandlesFunction.class);
			if (annotation != null)
			{
				annotatedMethods.add(new ImmutablePair<>(method.getName(), annotation));
			}
		}
		//hopefully not null?
		for (Method method : methods)
		{
			var annotations = method.getDeclaredAnnotations();
			if (annotations.length > 0)
			{
				Annotation a = annotations[0];
				a = annotations[0];
				var handlesFunction = (HandlesFunction) a;
				annotatedMethods.add(new ImmutablePair<>(method.getName(), handlesFunction));
			}
		}
		return annotatedMethods;
	}
}
