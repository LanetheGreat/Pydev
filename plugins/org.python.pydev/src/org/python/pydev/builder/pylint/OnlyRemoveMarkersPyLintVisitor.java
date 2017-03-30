/**
 * Copyright (c) 2017 Brainwy Software Ltda. All Rights Reserved.
 * Licensed under the terms of the Eclipse Public License (EPL).
 * Please see the license.txt included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package org.python.pydev.builder.pylint;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.python.pydev.core.log.Log;

public class OnlyRemoveMarkersPyLintVisitor implements IPyLintVisitor {

    public static final String PYLINT_PROBLEM_MARKER = "org.python.pydev.pylintproblemmarker";

    public static final String PYLINT_MESSAGE_ID = "pylint_message_id";

    protected IResource resource;

    /*default*/ public OnlyRemoveMarkersPyLintVisitor(IResource resource) {
        this.resource = resource;
    }

    @Override
    public void deleteMarkers() {
        //Whenever PyLint is passed, the markers will be deleted.
        try {
            if (resource != null) {
                resource.deleteMarkers(PYLINT_PROBLEM_MARKER, false, IResource.DEPTH_ZERO);
            }
        } catch (CoreException e3) {
            Log.log(e3);
        }
    }

    @Override
    public void startVisit() {
        deleteMarkers();
    }

    @Override
    public void join() {
        //no-op
    }

}