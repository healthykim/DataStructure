import java.io.*;
import java.util.LinkedList;

public class Matching
{
	static File inputFile; //이거 괜찮은 건지?
	static MyHashTable<Pair<Integer>, String> dataStructure;
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				command(input);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}


	//선언부를 exception throw 하게 고쳤는데 괜찮나?
	private static void command(String input) throws IOException {
		if(input.charAt(0)=='<'){
			inputFile = new File(input.split(" ")[1]);
			BufferedReader bufferdReader = new BufferedReader(new FileReader(inputFile));
			//make new data structure
			dataStructure = new MyHashTable<Pair<Integer>, String>();

			String line;
			int i = 1;
			while ((line = bufferdReader.readLine())!=null){
				for(int start=0; start<=line.length()-6;start++){
					Pair<Integer> pair = new Pair<>(i, start+1);
					dataStructure.insert(pair, line.substring(start, start+6));
				}
				i++;
			}
		}
		else if(input.charAt(0)=='@'){
			int slotNumber = Integer.parseInt(input.split(" ")[1]);
			traverse(slotNumber);
		}
		else if(input.charAt(0)=='?'){
			String pattern = input.substring(2);
			search(pattern);
		}
		else
			throw new IOException("명령이 주어진 형식에 어긋남");
	}

	private static void traverse(int slotNumber){
		//find the slot preorder traversal
		if(dataStructure.table[slotNumber]==null)
			System.out.println("EMPTY");
		else {
			MyAVLTree<Pair<Integer>, String> avlTree = dataStructure.table[slotNumber].tree;
			if (avlTree.isEmpty()) {
				System.out.println("EMPTY");
			} else {
				avlTree.traverse();
			}
		}
	}
	private static void search(String pattern){
		boolean isPattern = false;
		String initialSubstring = pattern.substring(0, Math.min(6, pattern.length()));
		isPattern = isTherePattern(initialSubstring);
		if(isPattern){
			int slot = dataStructure.search(initialSubstring);
			
			TreeNode<Pair<Integer>, String> treeNode = dataStructure.table[slot].tree.search(initialSubstring);
			MyLinkedList<Pair<Integer>> CandidateSet = new MyLinkedList<>();
			for(int j=0; j<treeNode.item.size(); j++){
				//System.out.println(treeNode.item.get(j));
				CandidateSet.append(treeNode.item.get(j));
			}
			for(int i=6; i< pattern.length(); ){
				String anotherSubstring = pattern.substring(i, Math.min(i+6, pattern.length()));
				if(anotherSubstring.length()<6){
					anotherSubstring = pattern.substring(pattern.length()-6);
					isPattern = isTherePattern(anotherSubstring);
					if (isPattern) {
						int slot2 = dataStructure.search(anotherSubstring);
						TreeNode<Pair<Integer>, String> CandidateSet2 = dataStructure.table[slot2].tree.search(anotherSubstring);
//						for (int j = 0; j < CandidateSet2.item.size(); j++) {
//							System.err.print(CandidateSet2.item.get(j));
//						}
//						System.err.println();
						for (int j = 0; j < CandidateSet.size();) {
								//System.out.println(CandidateSet.get(j));
							if (CandidateSet2.item.indexOf(new Pair<Integer>(CandidateSet.get(j).first, CandidateSet.get(j).second + pattern.length()-6)) == -1) {
								CandidateSet.remove(j);
							}
							else j++;
						}
//						for (int j = 0; j < CandidateSet.size(); j++) {
//							System.out.println(CandidateSet.get(j));
//						}
						i += 6;
					} else {
						System.out.println("(0, 0)");
						return;
					}
				}
				else {
					isPattern = isTherePattern(anotherSubstring);
					if (isPattern) {
						int slot2 = dataStructure.search(anotherSubstring);
						TreeNode<Pair<Integer>, String> CandidateSet2 = dataStructure.table[slot2].tree.search(anotherSubstring);
						for (int j = 0; j < CandidateSet.size();) {
							if (CandidateSet2.item.indexOf(new Pair<>(CandidateSet.get(j).first, CandidateSet.get(j).second + i)) == -1) {
								CandidateSet.remove(j);
							}
							else j++;
						}
						i += 6;
					} else {
						System.out.println("(0, 0)");
						return;
					}
				}
			}
			if(CandidateSet.isEmpty()){
				System.out.println("(0, 0)");
			}
			else {
				System.out.print(CandidateSet.get(0));
				for (int j = 1; j < CandidateSet.size(); j++) {
					System.out.print(" ");
					System.out.print(CandidateSet.get(j));
				}
				System.out.println();
			}
		}
		else System.out.println("(0, 0)");

	}
	private static boolean isTherePattern(String pattern){
		int slot = dataStructure.search(pattern);
		//System.out.println(pattern);
		if(slot<0||dataStructure.table[slot]==null||dataStructure.table[slot].tree==null) {

			return false;
		}
		else {
			TreeNode<Pair<Integer>, String> treeNode = dataStructure.table[slot].tree.search(pattern);
			if(treeNode!=null) {
				return true;
			}
			else {return false;}
		}
	}

}

