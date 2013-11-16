package FrontEnd;


/**
* FrontEnd/FrontEndInterfacePOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from FrontEnd.idl
* Friday, November 8, 2013 8:12:02 PM EST
*/

public abstract class FrontEndInterfacePOA extends org.omg.PortableServer.Servant
 implements FrontEnd.FrontEndInterfaceOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("executeOperation", new java.lang.Integer (0));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // FrontEnd/FrontEndInterface/executeOperation
       {
         String method = in.read_string ();
         String badgeId = in.read_string ();
         String firstName = in.read_string ();
         String lastName = in.read_string ();
         String description = in.read_string ();
         String status = in.read_string ();
         String address = in.read_string ();
         String lastDate = in.read_string ();
         String lastLocation = in.read_string ();
         String recordId = in.read_string ();
         String remoteServerName = in.read_string ();
         String $result = null;
         $result = this.executeOperation (method, badgeId, firstName, lastName, description, status, address, lastDate, lastLocation, recordId, remoteServerName);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:FrontEnd/FrontEndInterface:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public FrontEndInterface _this() 
  {
    return FrontEndInterfaceHelper.narrow(
    super._this_object());
  }

  public FrontEndInterface _this(org.omg.CORBA.ORB orb) 
  {
    return FrontEndInterfaceHelper.narrow(
    super._this_object(orb));
  }


} // class FrontEndInterfacePOA