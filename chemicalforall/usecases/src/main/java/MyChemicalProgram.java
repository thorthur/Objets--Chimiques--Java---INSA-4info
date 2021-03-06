
import java.util.Iterator;

import fr.insa.rennes.info.chemical.backend.Solution;


public class MyChemicalProgram {

	public static void main(String[] args) {	
		Position p1 = new Position(3,3);
		Position p2 = new Position(5,8);
		Position p3 = new Position(11,6);
		Position p4 = new Position(1,42);
		Position p5 = new Position(0,1);
		Position p6 = new Position(7,6);
		Position p7 = new Position(0,1);
		
		Solution maSolution = new Solution();
		Solution maSousSolution1 = new Solution();
		Solution maSousSolution2 = new Solution();
		Solution maSousSolution3 = new Solution();
		
		maSousSolution1.add(p1);
		maSousSolution1.add(p2);
		maSousSolution1.add(new MoyenneDesPosRR());
		
		maSousSolution2.add(p3);
		maSousSolution2.add(p4);
		maSousSolution2.add(new MoyenneDesPosRR());
		
		maSousSolution3.add(p5);
		maSousSolution3.add(p6);
		maSousSolution3.add(p7);
		maSousSolution3.add(new MoyenneDesPosRR());
		
		maSolution.add(maSousSolution1);
		maSolution.add(maSousSolution2);
		maSolution.add(maSousSolution3);
		maSolution.add(new MinRR());
		
		Iterator<Object> it1 = maSousSolution3.iterator();
		Iterator<Object> it2 = maSousSolution3.iterator();
		while(it1.hasNext()) {
			Object obj1 = it1.next();
			while(it2.hasNext()) {
				Object obj2 = it2.next();
				if(obj1 != obj2 && obj1.equals(obj2))
					maSousSolution3.remove(obj2);
			}
		}
			
		
		System.out.println(maSolution.toString());
		
		maSolution.react();
		
		while(!maSolution.is_inert())
			System.out.println(maSolution.toString());	
				
		System.out.println(maSolution.toString());	
		
	}

}